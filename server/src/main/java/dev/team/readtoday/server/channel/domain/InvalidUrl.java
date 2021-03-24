package dev.team.readtoday.server.channel.domain;

import java.io.Serial;

public class InvalidUrl extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -1311718388013951194L;

  InvalidUrl(String message) {
    super(message);
  }
}
