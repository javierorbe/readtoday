package dev.team.readtoday.server.jwt.application.get;

import dev.team.readtoday.server.jwt.domain.JwtToken;
import dev.team.readtoday.server.shared.domain.bus.query.QueryResponse;

public final class GetJwtTokenQueryResponse implements QueryResponse {

  private final String jwtToken;

  public GetJwtTokenQueryResponse(JwtToken token) {
    this.jwtToken = token.toString();
  }

  public String getJwtToken() {
    return jwtToken;
  }
}
