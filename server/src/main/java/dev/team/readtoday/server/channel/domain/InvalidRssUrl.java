package dev.team.readtoday.server.channel.domain;

import java.io.Serial;

public class InvalidRssUrl extends RuntimeException {


  @Serial
  private static final long serialVersionUID = -97648641568113697L;

  public InvalidRssUrl(String msg) {
    super(msg);
  }
}
