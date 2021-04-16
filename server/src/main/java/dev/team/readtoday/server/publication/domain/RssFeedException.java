package dev.team.readtoday.server.publication.domain;

import java.io.Serial;

public final class RssFeedException extends Exception {

  @Serial
  private static final long serialVersionUID = 6602128598660809122L;

  public RssFeedException(String message, Exception exception) {
    super(message, exception);
  }
}
