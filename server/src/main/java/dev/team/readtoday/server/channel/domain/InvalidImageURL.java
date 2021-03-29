package dev.team.readtoday.server.channel.domain;

import java.io.Serial;

public class InvalidImageURL extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 7907956654996583901L;

  public InvalidImageURL(String message) {
    super(message);
  }
}
