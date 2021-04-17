package dev.team.readtoday.server.publication.infrastructure.controller.channel;

import com.google.common.collect.ImmutableList;
import dev.team.readtoday.server.publication.domain.Publication;
import java.util.List;

public final class ChannelPublicationsFoundEvent {

  private final ImmutableList<Publication> publications;

  public ChannelPublicationsFoundEvent(List<Publication> publications) {
    this.publications = ImmutableList.copyOf(publications);
  }

  public ImmutableList<Publication> getPublications() {
    return publications;
  }
}
