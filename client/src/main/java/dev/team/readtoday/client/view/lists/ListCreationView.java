package dev.team.readtoday.client.view.lists;

import dev.team.readtoday.client.usecase.customlist.create.events.CustomListCreatedSuccessfullyEvent;
import dev.team.readtoday.client.usecase.customlist.create.events.CustomListCreationEvent;
import dev.team.readtoday.client.usecase.customlist.create.events.CustomListCreationFailedEvent;
import dev.team.readtoday.client.usecase.customlist.create.messages.CustomListCreationRequest;
import dev.team.readtoday.client.view.AlertLauncher;
import dev.team.readtoday.client.view.ViewController;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public final class ListCreationView implements ViewController, Initializable {
  private final EventBus eventBus;

  @FXML
  private TextField name;

  @FXML
  private Button closeButton;

  public ListCreationView(EventBus eventBus) {
    this.eventBus = eventBus;
    eventBus.register(this);

  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Objects.requireNonNull(name);
  }

  @FXML
  public void createList() {
    CustomListCreationRequest request = new CustomListCreationRequest(name.getText());
    eventBus.post(new CustomListCreationEvent(request));
  }

  @FXML
  public void cancelCreation() {
    Stage stage = (Stage) closeButton.getScene().getWindow();
    stage.close();
  }

  @Subscribe
  public void onCustomListSuccessfullyCreatedEvent(CustomListCreatedSuccessfullyEvent event) {
    AlertLauncher.info("List was created");
  }

  @Subscribe
  public void onCustomListCreationFailedEvent(CustomListCreationFailedEvent event) {
    AlertLauncher.error("List creation failed", event.getReason());
  }
}

