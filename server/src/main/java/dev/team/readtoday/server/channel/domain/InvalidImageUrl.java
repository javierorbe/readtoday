package dev.team.readtoday.server.channel.domain;

import java.io.Serial;

final class InvalidImageUrl extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 7907956654996583901L;

  InvalidImageUrl(String message) {
    super(message);
  }

  InvalidImageUrl(String message, Exception e) {
    super(message, e);
  }
}
