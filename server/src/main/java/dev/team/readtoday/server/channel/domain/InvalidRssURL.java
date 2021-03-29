package dev.team.readtoday.server.channel.domain;

import java.io.Serial;

public class InvalidRssURL extends RuntimeException {


  @Serial
  private static final long serialVersionUID = -97648641568113697L;

  public InvalidRssURL(String msg) {
    super(msg);
  }
}
