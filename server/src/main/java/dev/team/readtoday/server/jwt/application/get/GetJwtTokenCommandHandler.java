package dev.team.readtoday.server.jwt.application.get;

import dev.team.readtoday.server.jwt.domain.JwtToken;
import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.shared.domain.bus.query.QueryHandler;

@Service
public final class GetJwtTokenCommandHandler
    implements QueryHandler<GetJwtTokenQuery, GetJwtTokenQueryResponse> {

  private final GetJwtToken getJwtToken;

  public GetJwtTokenCommandHandler(GetJwtToken getJwtToken) {
    this.getJwtToken = getJwtToken;
  }

  @Override
  public GetJwtTokenQueryResponse handle(GetJwtTokenQuery command) {
    UserId userId = UserId.fromString(command.getUserId());
    JwtToken token = getJwtToken.apply(userId);
    return new GetJwtTokenQueryResponse(token);
  }
}
