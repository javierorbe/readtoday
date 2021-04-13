package dev.team.readtoday.server.subscription.domain;

import java.io.Serial;

public final class SubscriptionAlreadyExists extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -7644037051032663257L;

  public SubscriptionAlreadyExists(String message) {
    super(message);
  }
}
