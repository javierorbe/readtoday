package dev.team.readtoday.client.view.home;

import dev.team.readtoday.client.model.Channel;
import dev.team.readtoday.client.model.Publication;
import dev.team.readtoday.client.view.ViewController;
import java.util.List;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

final class PublicationListWindow implements ViewController {

  private static final double WINDOW_WIDTH = 600.0;
  private static final double WINDOW_HEIGHT = 400.0;
  private static final double CONTENT_SPACING = 6.0;

  private final Stage stage = new Stage();

  private PublicationListWindow(Channel channel, List<Publication> publications) {
    VBox container = new VBox();
    ScrollPane root = new ScrollPane(container);
    container.setPadding(new Insets(CONTENT_SPACING));
    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    Node[] publicationNodes = buildPublicationNodeArray(scene, publications);
    container.getChildren().addAll(publicationNodes);

    stage.setScene(scene);
    stage.setTitle(String.format("Publications of %s", channel.getName()));
  }

  /** Open a publication list window of a channel with its publications. */
  static void open(Channel channel, List<Publication> publications) {
    Platform.runLater(() -> {
      PublicationListWindow window = new PublicationListWindow(channel, publications);
      window.stage.show();
    });
  }

  private static Node[] buildPublicationNodeArray(Scene scene, List<Publication> publications) {
    Node[] nodes = new Node[publications.size()];

    for (int i = 0; i < publications.size(); i++) {
      Publication publication = publications.get(i);
      nodes[i] = new PublicationNode(publication, scene.widthProperty());
    }

    return nodes;
  }
}
