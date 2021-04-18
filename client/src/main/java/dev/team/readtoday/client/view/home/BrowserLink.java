package dev.team.readtoday.client.view.home;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import javafx.scene.control.Hyperlink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class BrowserLink extends Hyperlink {

  private static final Logger LOGGER = LoggerFactory.getLogger(BrowserLink.class);

  private final URI link;

  BrowserLink(String text, String link) {
    super(text);
    this.link = URI.create(link);
    setOnAction(event -> openInBrowser());
  }

  private void openInBrowser() {
    try {
      Desktop.getDesktop().browse(link);
    } catch (IOException e) {
      LOGGER.error("Error opening browser link.", e);
    }
  }
}
