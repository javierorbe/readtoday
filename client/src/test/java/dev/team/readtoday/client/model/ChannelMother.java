package dev.team.readtoday.client.model;

import java.util.Set;
import java.util.UUID;

final class ChannelMother {

  static Channel withName(String name) {
    return new Channel(
        UUID.randomUUID(),
        name,
        "https://picsum.photos/128",
        Set.of()
    );
  }
}
