package dev.team.readtoday.server.publication.infrastructure.formatter;

import static j2html.TagCreator.a;
import static j2html.TagCreator.div;
import static j2html.TagCreator.h3;
import static j2html.TagCreator.p;

import dev.team.readtoday.server.publication.application.formatted.PublicationFormatter;
import dev.team.readtoday.server.publication.domain.Publication;
import dev.team.readtoday.server.publication.domain.PublicationDescription;
import dev.team.readtoday.server.publication.domain.PublicationTitle;
import dev.team.readtoday.server.shared.domain.Service;
import j2html.tags.ContainerTag;

@Service
public final class HtmlPublicationFormatter implements PublicationFormatter {

  private static final PublicationTitle DEFAULT_TITLE = new PublicationTitle("Not available");
  private static final PublicationDescription DEFAULT_DESCRIPTION =
      new PublicationDescription("Not available");

  @Override
  public String format(Publication publication) {
    String title = publication.getTitle().orElse(DEFAULT_TITLE).toString();
    ContainerTag titleTag = a(title);

    if (publication.getLink().isPresent()) {
      titleTag = titleTag.withHref(publication.getLink().get().toString());
    }

    String description = publication.getDescription().orElse(DEFAULT_DESCRIPTION).toString();

    return div(
        h3(titleTag),
        p(description)
    ).render();
  }
}
