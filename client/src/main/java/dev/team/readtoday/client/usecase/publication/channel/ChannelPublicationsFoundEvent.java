package dev.team.readtoday.client.usecase.publication.channel;

import com.google.common.collect.ImmutableList;
import dev.team.readtoday.client.model.Publication;

public final class ChannelPublicationsFoundEvent {

  private final String channelId;
  private final ImmutableList<Publication> publications;

  ChannelPublicationsFoundEvent(String channelId, ImmutableList<Publication> publications) {
    this.channelId = channelId;
    this.publications = publications;
  }

  public String getChannelId() {
    return channelId;
  }

  public ImmutableList<Publication> getPublications() {
    return publications;
  }
}
