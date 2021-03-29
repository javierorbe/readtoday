package dev.team.readtoday.server.channel.domain;

import java.io.Serial;

public class InvalidChannelTitle extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 7335487576407955200L;

  InvalidChannelTitle(String message) {
    super(message);
  }
}
