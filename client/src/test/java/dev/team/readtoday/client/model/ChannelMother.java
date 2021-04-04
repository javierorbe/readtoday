package dev.team.readtoday.client.model;

import java.util.Set;
import java.util.UUID;

final class ChannelMother {

  static Channel withName(String name) {
    return new Channel(
        UUID.randomUUID().toString(),
        name,
        "https://picsum.photos/128",
        "Some random description.",
        "https://www.redditstatic.com/desktop2x/img/favicon/favicon-16x16.png",
        Set.of()
    );
  }
}
