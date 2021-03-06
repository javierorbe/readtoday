package dev.team.readtoday.server.shared.domain;

import java.util.UUID;

public final class ChannelId extends Identifier {

  private ChannelId(UUID value) {
    super(value);
  }

  public static ChannelId random() {
    return new ChannelId(UUID.randomUUID());
  }

  public static ChannelId fromString(String uuid) {
    return new ChannelId(UUID.fromString(uuid));
  }
}
