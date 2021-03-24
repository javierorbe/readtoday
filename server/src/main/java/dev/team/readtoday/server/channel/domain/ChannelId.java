package dev.team.readtoday.server.channel.domain;

import dev.team.readtoday.server.shared.domain.Identifier;
import java.util.UUID;

public class ChannelId extends Identifier {

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
