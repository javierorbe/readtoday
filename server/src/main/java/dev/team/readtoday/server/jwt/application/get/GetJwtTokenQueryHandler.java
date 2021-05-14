package dev.team.readtoday.server.jwt.application.get;

import dev.team.readtoday.server.jwt.domain.JwtToken;
import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.shared.domain.bus.query.QueryHandler;

@Service
public final class GetJwtTokenQueryHandler
    implements QueryHandler<GetJwtTokenQuery, GetJwtTokenQueryResponse> {

  private final GetJwtToken getJwtToken;

  public GetJwtTokenQueryHandler(GetJwtToken getJwtToken) {
    this.getJwtToken = getJwtToken;
  }

  @Override
  public GetJwtTokenQueryResponse handle(GetJwtTokenQuery query) {
    UserId userId = UserId.fromString(query.getUserId());
    JwtToken token = getJwtToken.apply(userId);
    return new GetJwtTokenQueryResponse(token);
  }
}
