package dev.team.readtoday.server.subscription.domain;

import java.io.Serial;

public final class SubscriptionNotFound extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 7246770140926721279L;

  public SubscriptionNotFound(String message) {
    super(message);
  }
}
