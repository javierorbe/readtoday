package dev.team.readtoday.client.view.admin;

import dev.team.readtoday.client.usecase.category.create.events.CategoryCreationEvent;
import dev.team.readtoday.client.usecase.category.create.events.CategoryCreationFailedEvent;
import dev.team.readtoday.client.usecase.category.create.events.CategorySuccessfullyCreatedEvent;
import dev.team.readtoday.client.usecase.category.create.messages.CategoryCreationRequest;
import dev.team.readtoday.client.view.AlertLauncher;
import dev.team.readtoday.client.view.ViewController;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public final class CategoryCreationView implements ViewController, Initializable {
  private final EventBus eventBus;

  @FXML
  private TextField name;

  public CategoryCreationView(EventBus eventBus) {
    this.eventBus = eventBus;
    eventBus.register(this);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Objects.requireNonNull(name);
  }

  @FXML
  public void createCategory() {
    CategoryCreationRequest request = new CategoryCreationRequest(name.getText());
    eventBus.post(new CategoryCreationEvent(request));
  }

  @Subscribe
  public void onCategorySuccessfullyCreatedEvent(CategorySuccessfullyCreatedEvent event) {
    AlertLauncher.info("Category was created");
  }

  @Subscribe
  public void onCategoryCreationFailedEvent(CategoryCreationFailedEvent event) {
    AlertLauncher.error("Category creation failed", event.getReason());
  }
}
