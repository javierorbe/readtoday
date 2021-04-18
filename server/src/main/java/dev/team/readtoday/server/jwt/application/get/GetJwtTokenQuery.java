package dev.team.readtoday.server.jwt.application.get;

import dev.team.readtoday.server.shared.domain.bus.query.Query;

public final class GetJwtTokenQuery implements Query<GetJwtTokenQueryResponse> {

  private final String userId;

  public GetJwtTokenQuery(String userId) {
    this.userId = userId;
  }

  public String getUserId() {
    return userId;
  }
}
