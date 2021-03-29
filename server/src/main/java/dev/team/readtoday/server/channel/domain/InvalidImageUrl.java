package dev.team.readtoday.server.channel.domain;

import java.io.Serial;

public class InvalidImageUrl extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 7907956654996583901L;

  public InvalidImageUrl(String message) {
    super(message);
  }
}
