package dev.team.readtoday.client.usecase.publication.channel;

public final class SearchChannelPublicationsEvent {

  private final String channelId;

  public SearchChannelPublicationsEvent(String channelId) {
    this.channelId = channelId;
  }

  String getChannelId() {
    return channelId;
  }
}
