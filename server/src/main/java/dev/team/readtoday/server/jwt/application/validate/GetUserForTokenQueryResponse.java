package dev.team.readtoday.server.jwt.application.validate;

import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.shared.domain.bus.query.QueryResponse;

public final class GetUserForTokenQueryResponse implements QueryResponse {

  private final String userId;

  GetUserForTokenQueryResponse(UserId userId) {
    this.userId = userId.toString();
  }

  public String getUserId() {
    return userId;
  }
}
