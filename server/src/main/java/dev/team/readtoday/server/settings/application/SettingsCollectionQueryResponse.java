package dev.team.readtoday.server.settings.application;

import dev.team.readtoday.server.shared.domain.bus.query.QueryResponse;
import java.util.Collection;
import java.util.Collections;

public final class SettingsCollectionQueryResponse implements QueryResponse {

  private final Collection<SettingsQueryResponse> collection;

  public SettingsCollectionQueryResponse(Collection<SettingsQueryResponse> collection) {
    this.collection = Collections.unmodifiableCollection(collection);
  }

  public Collection<SettingsQueryResponse> getCollection() {
    return collection;
  }
}
