package dev.team.readtoday.client.view.home;

import dev.team.readtoday.client.model.Publication;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.apache.commons.text.StringEscapeUtils;

final class PublicationNode extends VBox {

  private static final double ELEMENT_SPACING = 2.0;
  private static final double VERTICAL_SPACING = 6.0;
  private static final double HORIZONTAL_SPACING = 2.0;
  private static final double TITLE_FONT_SIZE = 13.0;
  private static final String DATE_TEXT_COLOR = "#757575";

  /**
   * Constructs a node that displays a publication.
   *
   * @param publication the publication to display
   * @param maxWidthProperty property with the maximum width of this node
   */
  PublicationNode(Publication publication, ObservableValue<? extends Number> maxWidthProperty) {
    super(ELEMENT_SPACING);

    BrowserLink title = new BrowserLink(publication.getTitle(), publication.getLink());
    title.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, TITLE_FONT_SIZE));

    String unescapedDescription = StringEscapeUtils.unescapeHtml4(publication.getDescription());
    Label description = new Label(unescapedDescription);
    description.setWrapText(true);
    description.maxWidthProperty().bind(maxWidthProperty);

    Label date = new Label(String.format("Published on %s", formatDate(publication.getDate())));
    date.setTextFill(Color.valueOf(DATE_TEXT_COLOR));

    getChildren().add(title);
    getChildren().add(description);
    getChildren().add(date);

    setPadding(new Insets(
        VERTICAL_SPACING,
        HORIZONTAL_SPACING,
        VERTICAL_SPACING,
        HORIZONTAL_SPACING
    ));
  }

  private static String formatDate(OffsetDateTime dateTime) {
    return dateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
  }
}
