package dev.team.readtoday.server.jwt.application.validate;

import dev.team.readtoday.server.shared.domain.bus.query.Query;

public final class GetUserForTokenQuery implements Query<GetUserForTokenQueryResponse> {

  private final String jwtToken;

  public GetUserForTokenQuery(String jwtToken) {
    this.jwtToken = jwtToken;
  }

  String getJwtToken() {
    return jwtToken;
  }
}
