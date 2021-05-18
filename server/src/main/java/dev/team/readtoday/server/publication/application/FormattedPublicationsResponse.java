package dev.team.readtoday.server.publication.application;

import dev.team.readtoday.server.shared.domain.bus.query.QueryResponse;
import java.util.Collections;
import java.util.List;

public final class FormattedPublicationsResponse implements QueryResponse {

  private final List<String> formattedPublications;

  /**
   * Constructs a {@code FormattedPublicationsResponse}.
   *
   * @param publications formatted publications
   */
  public FormattedPublicationsResponse(List<String> publications) {
    this.formattedPublications = Collections.unmodifiableList(publications);
  }

  public List<String> getFormattedPublications() {
    return formattedPublications;
  }
}
