package dev.team.readtoday.client.view.home;

import dev.team.readtoday.client.model.Category;
import dev.team.readtoday.client.model.Publication;
import dev.team.readtoday.client.usecase.customlist.create.messages.CustomListCreationRequest;
import dev.team.readtoday.client.usecase.readlater.ReadLaterRequest;
import dev.team.readtoday.client.usecase.readlater.SaveReadLaterListFailedEvent;
import dev.team.readtoday.client.usecase.readlater.SaveReadLaterListRequestedEvent;
import dev.team.readtoday.client.usecase.readlater.SuccessfulSaveReadLaterListEvent;
import dev.team.readtoday.client.view.AlertLauncher;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.apache.commons.text.StringEscapeUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class PublicationNode extends VBox {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(PublicationNode.class);

  private static final double ELEMENT_SPACING = 2.0;
  private static final double VERTICAL_SPACING = 6.0;
  private static final double HORIZONTAL_SPACING = 2.0;
  private static final double TITLE_FONT_SIZE = 13.0;
  private static final String DATE_TEXT_COLOR = "#757575";
  private final EventBus eventBus;
  private final Publication publication;

  /**
   * Constructs a node that displays a publication.
   *
   * @param publication      the publication to display
   * @param maxWidthProperty property with the maximum width of this node
   */
  PublicationNode(EventBus eventBus, Publication publication,
      ObservableValue<? extends Number> maxWidthProperty) {
    super(ELEMENT_SPACING);

    this.publication = publication;
    this.eventBus = eventBus;

    BrowserLink title = new BrowserLink(publication.getTitle(), publication.getLink());
    title.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, TITLE_FONT_SIZE));

    String unescapedDescription = StringEscapeUtils.unescapeHtml4(publication.getDescription());
    Label description = new Label(unescapedDescription);
    description.setWrapText(true);
    description.maxWidthProperty().bind(maxWidthProperty);

    Label date = new Label(String.format("Published on %s", formatDate(publication.getDate())));
    date.setTextFill(Color.valueOf(DATE_TEXT_COLOR));

    Button readLaterButton = new Button("Read Later");
    readLaterButton.setOnAction(this::readLaterHandler);

    Button addlistButton = new Button("Add to list");
    readLaterButton.setOnAction(this::customListHandler);

    getChildren().add(title);
    getChildren().add(description);
    getChildren().add(date);
    getChildren().add(readLaterButton);
    getChildren().add(addlistButton);

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

  private void readLaterHandler(ActionEvent event) {
    LOGGER.trace("Read later was clicked");
    String publicationId = publication.getId();
    String title = publication.getTitle();
    String description = publication.getDescription();
    OffsetDateTime date = publication.getDate();
    String link = publication.getLink();
    Collection<String> categories = publication.getCategories().stream().map(Category::getId).collect(
        Collectors.toList());
    ReadLaterRequest request = new ReadLaterRequest(publicationId, title, description, date, link, categories);
    SaveReadLaterListRequestedEvent saveEvent = new SaveReadLaterListRequestedEvent(request);
    eventBus.post(saveEvent);
    // TODO: Send an event to read later here
  }

  private void customListHandler(ActionEvent event){
    LOGGER.trace("CustomList add was clicked");


  }
}
