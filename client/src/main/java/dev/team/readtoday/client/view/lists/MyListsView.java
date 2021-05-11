package dev.team.readtoday.client.view.lists;

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
import dev.team.readtoday.client.usecase.readlater.SaveReadLaterListFailedEvent;
import dev.team.readtoday.client.usecase.readlater.SuccessfulSaveReadLaterListEvent;
import dev.team.readtoday.client.usecase.readlater.get.SuccesfulGetReadLaterPublicationsEvent;
import dev.team.readtoday.client.usecase.settings.get.FailedToGetSettingsEvent;
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
import dev.team.readtoday.client.usecase.subscription.unsubscribe.DeleteSubscriptionFailedEvent;
import dev.team.readtoday.client.usecase.subscription.unsubscribe.DeleteSubscriptionSuccessfulEvent;
import dev.team.readtoday.client.view.AlertLauncher;
import dev.team.readtoday.client.view.ViewController;
import dev.team.readtoday.client.view.admin.CategoryCreationView;
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

public final class MyListsView implements ViewController, Initializable {

  private final EventBus eventBus;

  private final Stage listCreationStage;



  public MyListsView(EventBus eventBus) {
    this.eventBus = eventBus;
    eventBus.register(this);
    listCreationStage = new Stage();
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

  }


  @FXML
  public void createList() {
    Scene listCreationScene = SceneCreator.createScene(
        "listCreation.fxml",
        new ListCreationView(eventBus)
    );

    listCreationStage.setHeight(150);
    listCreationStage.setWidth(300);
    listCreationStage.setTitle("List Creation");
    listCreationStage.setScene(listCreationScene);
    listCreationStage.show();
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
  public void goToHome() { eventBus.post(new ChangeSceneEvent(SceneType.HOME));
  }



  @Subscribe
  public void FailedReadLaterHandler(SaveReadLaterListFailedEvent event) {
    AlertLauncher.info("Publication was not added to the read later list");
  }
}


