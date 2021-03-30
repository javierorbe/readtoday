package dev.team.readtoday.server.channel.domain;

import java.io.Serial;

public final class InvalidRssUrl extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -97648641568113697L;

  InvalidRssUrl(String msg) {
    super(msg);
  }
}
