package dev.team.readtoday.client.view.home;

import com.google.common.collect.ImmutableList;
import dev.team.readtoday.client.app.gui.ChangeSceneEvent;
import dev.team.readtoday.client.app.gui.SceneCreator;
import dev.team.readtoday.client.app.gui.SceneType;
import dev.team.readtoday.client.model.Category;
import dev.team.readtoday.client.model.Channel;
import dev.team.readtoday.client.model.NotificationPreference;
import dev.team.readtoday.client.model.Publication;
import dev.team.readtoday.client.model.Settings;
import dev.team.readtoday.client.usecase.auth.SignedOutEvent;
import dev.team.readtoday.client.usecase.auth.signin.SuccessfulSignInEvent;
import dev.team.readtoday.client.usecase.auth.signup.SuccessfulSignUpEvent;
import dev.team.readtoday.client.usecase.category.search.events.SearchAllCategoriesEvent;
import dev.team.readtoday.client.usecase.channel.search.events.SearchChannelsByCategoryEvent;
import dev.team.readtoday.client.usecase.channel.search.events.SearchChannelsByCategoryFailedEvent;
import dev.team.readtoday.client.usecase.channel.search.events.SearchChannelsByCategorySuccessfullyEvent;
import dev.team.readtoday.client.usecase.settings.get.SettingsReceivedEvent;
import dev.team.readtoday.client.usecase.shared.response.CategoryResponse;
import dev.team.readtoday.client.usecase.shared.response.PublicationResponse;
import dev.team.readtoday.client.usecase.subscription.publications.PublicationRequestEvent;
import dev.team.readtoday.client.usecase.subscription.publications.PublicationRequestFailedEvent;
import dev.team.readtoday.client.usecase.subscription.publications.PublicationRequestSuccesfulEvent;
import dev.team.readtoday.client.usecase.subscription.search.events.MySubscriptionsListRequestedEvent;
import dev.team.readtoday.client.usecase.subscription.search.events.SuccessfulMySubscriptionsListEvent;
import dev.team.readtoday.client.usecase.subscription.subscribe.SubscriptionRequestedEvent;
import dev.team.readtoday.client.usecase.subscription.subscribe.SuccessfulSubscriptionEvent;
import dev.team.readtoday.client.usecase.subscription.unsubscribe.DeleteSubscriptionEvent;
import dev.team.readtoday.client.usecase.subscription.unsubscribe.DeleteSubscriptionSuccessfulEvent;
import dev.team.readtoday.client.view.AlertLauncher;
import dev.team.readtoday.client.view.ViewController;
import dev.team.readtoday.client.view.settings.SettingsView;
import java.net.URL;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HomeView implements ViewController, Initializable {

  private final EventBus eventBus;

  private final ImmutableList<Channel> allChannels;
  private final Map<Category, List<Channel>> categoryToChannel;

  @FXML
  private ListView<Channel> channelListView;
  private final ObservableList<Channel> observableChannelList;

  @FXML
  private ListView<Channel> newChannelListView;

  @FXML
  private ComboBox<Category> channelCategorySelector;

  @FXML
  private TextField channelsByCategory;

  Logger logger = LoggerFactory.getLogger(HomeView.class);

  public HomeView(EventBus eventBus, Collection<Channel> subscribedChannels) {
    this.eventBus = eventBus;
    allChannels = ImmutableList.sortedCopyOf(subscribedChannels);
    observableChannelList = FXCollections.observableArrayList(allChannels);
    categoryToChannel = createCategoryToChannelMap(subscribedChannels);

    eventBus.register(this);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    Objects.requireNonNull(channelListView);
    Objects.requireNonNull(newChannelListView);
    Objects.requireNonNull(channelCategorySelector);
    Objects.requireNonNull(channelsByCategory);

    // Set selector categories.
    List<Category> categories = new ArrayList<>(categoryToChannel.keySet());
    categories.sort(Comparator.naturalOrder());
    channelCategorySelector.getItems().addAll(categoryToChannel.keySet());

    // Initially, all channels are visible.
    channelListView.setItems(observableChannelList);

    channelCategorySelector.valueProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue == null) {
        observableChannelList.setAll(allChannels);
      } else {
        observableChannelList.setAll(categoryToChannel.get(newValue));
      }
    });

    channelListView.setCellFactory(listView -> new ChannelCell(eventBus));
    newChannelListView.setCellFactory(listView -> new ChannelCell(eventBus));
  }

  private static Map<Category, List<Channel>> createCategoryToChannelMap(
      Collection<Channel> channels) {
    Map<Category, List<Channel>> map = new HashMap<>(channels.size()); // Size approximation.

    for (Channel channel : channels) {
      for (Category category : channel.getCategories()) {
        List<Channel> categoryChannels =
            map.computeIfAbsent(category, ignored -> new ArrayList<>(10));
        categoryChannels.add(channel);
      }
    }

    map.forEach((category, channelList) -> channelList.sort(Comparator.naturalOrder()));

    return map;
  }

  @FXML
  public void unselectCategory() {
    channelCategorySelector.valueProperty().set(null);
  }

  @FXML
  public void performSearch() {
    String categoryName = channelsByCategory.getText();
    eventBus.post(new SearchChannelsByCategoryEvent(categoryName));
  }

  @FXML
  public void goToAdmin() {
    eventBus.post(new ChangeSceneEvent(SceneType.ADMIN));
    eventBus.post(new SearchAllCategoriesEvent());
  }

  @FXML
  private void signOut(ActionEvent event) {
    eventBus.post(new SignedOutEvent());
  }

  @FXML
  public void unsubscribe() {
    if (channelListView.getSelectionModel().getSelectedItem() != null) {
      Channel channel = channelListView.getSelectionModel().getSelectedItem();
      eventBus.post(new DeleteSubscriptionEvent(channel));
    }
  }

  @FXML
  public void subscribe() {
    if (newChannelListView.getSelectionModel().getSelectedItem() != null) {
      Channel channel = newChannelListView.getSelectionModel().getSelectedItem();
      eventBus.post(new SubscriptionRequestedEvent(channel));
    }
  }

  @FXML
  public void openSubsPublications() {
    eventBus.post(new PublicationRequestEvent());
  }

  @FXML
  public void openReadLaterList() { }

  @Subscribe
  public void onSearchResultReceived(SearchChannelsByCategorySuccessfullyEvent event) {
    ObservableList<Channel> list =
        FXCollections.observableList(new ArrayList<>(event.getChannels()));
    Platform.runLater(() -> newChannelListView.setItems(list));
  }

  @Subscribe
  public void onChannelSearchRequestFailed(SearchChannelsByCategoryFailedEvent event) {
    AlertLauncher.error("Category not found");
  }

  @Subscribe
  public void onSuccessfulSignUpEvent(SuccessfulSignUpEvent event) {
    eventBus.post(new MySubscriptionsListRequestedEvent());
  }

  @Subscribe
  public void onSuccessfulSignInEvent(SuccessfulSignInEvent event) {
    eventBus.post(new MySubscriptionsListRequestedEvent());
  }

  @Subscribe
  public void onSearchResultReceived(SuccessfulMySubscriptionsListEvent event) {
    ObservableList<Channel> list =
        FXCollections.observableList(new ArrayList<>(event.getSubscriptions()));
    Platform.runLater(() -> channelListView.setItems(list));
  }

  @Subscribe
  public void onSuccessfulSubscription(SuccessfulSubscriptionEvent event) {
    boolean present = false;
    ObservableList<Channel> list =
        FXCollections.observableList(new ArrayList<>(channelListView.getItems()));
    for (Channel c : list) {
      if (c.getId() == event.getChannel().getId()) {
        present = true;
      }
    }
    if (present == false) {
      list.add(event.getChannel());
      Platform.runLater(() -> channelListView.setItems(list));
    }

  }

  @Subscribe
  public void onSuccessfulDeleteSubscription(DeleteSubscriptionSuccessfulEvent event) {
    ObservableList<Channel> list =
        FXCollections.observableList(new ArrayList<>(channelListView.getItems()));
    list.remove(event.getChannel());
    Platform.runLater(() -> channelListView.setItems(list));
  }

  @Subscribe
  public void onSuccessfulPublicationRequest(PublicationRequestSuccesfulEvent event) {
    List<Publication> publications = new ArrayList<>();

    for (PublicationResponse p : event.getPublications()) {
      Set<Category> categories = new HashSet<>();

      for (CategoryResponse categoryResponse : p.getCategories()) {
        categories.add(new Category(categoryResponse.getId(), categoryResponse.getName()));
      }
      publications.add(new Publication(p.getId(), p.getTitle(),
          p.getDescription(), p.getDate(), p.getLink(), categories));
    }

    PublicationListWindow.open(eventBus, sortList(publications));
  }


  public List<Publication> sortList(List<Publication> publications) {
    List<Publication> sortedList = new ArrayList<>();
    List<OffsetDateTime> dateTimes = new ArrayList<>();
    for (Publication publication : publications) {
      dateTimes.add(publication.getDate());
    }
    Collections.sort(dateTimes);
    Collections.reverse(dateTimes);
    for (OffsetDateTime dateTime : dateTimes) {
      for (Publication publication : publications) {
        if (dateTime == publication.getDate()) {
          sortedList.add(publication);
        }
      }
    }
    return sortedList;
  }

  @Subscribe
  public void onFailedPublicationRequest(PublicationRequestFailedEvent event) {
    AlertLauncher.error("You are not subscribed to any channel");
  }


  @FXML
  private void getSettings() {
    // TODO: Delete these two lines when server get user settings functionality is completed.
    //       This is a simulation. Because server is not working now.
    ZoneId zone = ZoneId.of("Europe/Madrid");
    eventBus.post(new SettingsReceivedEvent(zone.getId(), NotificationPreference.NONE.toString()));

    // Uncomment me to get user settings from server -> open window to edit user settings.
    // eventBus.post(new GetSettingsEvent());
  }

  @Subscribe
  public void createSettingsStage(SettingsReceivedEvent event) {
    Stage settingsStage = new Stage();

    Settings settings = new Settings(event.getZoneId(), event.getNotificationPreference());

    Scene settingsScene = SceneCreator.createScene(
        "settings.fxml",
        new SettingsView(eventBus, settings)
    );

    settingsStage.setHeight(300);
    settingsStage.setWidth(500);
    settingsStage.setTitle("User Settings");
    settingsStage.setScene(settingsScene);
    settingsStage.show();
  }




  @Subscribe
  public void successfulReadLaterHandler(SuccessfulSaveReadLaterListEvent event){
    AlertLauncher.info("Publication was added to the read later list");
  }
  @Subscribe
  public void FailedReadLaterHandler(SaveReadLaterListFailedEvent event){
    AlertLauncher.info("Publication was not added to the read later list");
  }
}


