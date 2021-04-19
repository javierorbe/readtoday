package dev.team.readtoday.server.jwt.application.validate;

import dev.team.readtoday.server.jwt.domain.JwtToken;
import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.shared.domain.bus.query.QueryHandler;

@Service
public final class GetUserForTokenQueryHandler
    implements QueryHandler<GetUserForTokenQuery, GetUserForTokenCommandResponse> {

  private final GetUserForToken getUserForToken;

  public GetUserForTokenQueryHandler(GetUserForToken getUserForToken) {
    this.getUserForToken = getUserForToken;
  }

  @Override
  public GetUserForTokenCommandResponse handle(GetUserForTokenQuery query) {
    JwtToken token = JwtToken.fromString(query.getJwtToken());
    UserId userId = getUserForToken.apply(token);
    return new GetUserForTokenCommandResponse(userId);
  }
}
