package dev.team.readtoday.client.view.admin;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import dev.team.readtoday.client.create.ChannelCreationEvent;
import dev.team.readtoday.client.create.ChannelCreationRequest;
import dev.team.readtoday.client.create.ChannelCreationResponseEvent;
import dev.team.readtoday.client.navigation.ChangeSceneEvent;
import dev.team.readtoday.client.navigation.SceneType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public final class AdminView implements Initializable {

  @FXML
  private TextField title;
  @FXML
  private TextField rssUrl;
  @FXML
  private TextField description;
  @FXML
  private TextField imageUrl;

  // TODO: Adds categories ids

  private final EventBus eventBus;

  public AdminView(EventBus eventBus) {
    this.eventBus = eventBus;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }

  @FXML
  public void createChannel() {
    ChannelCreationRequest request = new ChannelCreationRequest(
        title.getText(),
        rssUrl.getText(),
        description.getText(),
        imageUrl.getText(),
        // TODO: Change for the real categories ids!
        Collections.emptyList()
    );

    eventBus.post(new ChannelCreationEvent(request));
  }

  @FXML
  public void exitAdminMode() {
    eventBus.post(new ChangeSceneEvent(SceneType.HOME));
  }

  // When server sends a response.
  @Subscribe
  public void onChannelCreationResponseReceived(ChannelCreationResponseEvent event) {
    Response response = event.getResponse();
    Status status = Status.fromStatusCode(response.getStatus());

    switch (status) {
      case UNAUTHORIZED -> System.out.println("You have no permission.");
      case BAD_REQUEST -> System.out.println("Invalid data.");
      case CREATED -> System.out.println("Channel was created.");
      default -> System.out.println("Status code not supported: " + status.getStatusCode());
    }
  }
}
