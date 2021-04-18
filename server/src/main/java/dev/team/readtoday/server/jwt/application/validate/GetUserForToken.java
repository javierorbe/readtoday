package dev.team.readtoday.server.jwt.application.validate;

import dev.team.readtoday.server.jwt.domain.JwtToken;
import dev.team.readtoday.server.jwt.domain.JwtTokenRepository;
import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.UserId;
import java.util.function.Function;

@Service
public final class GetUserForToken implements Function<JwtToken, UserId> {

  private final JwtTokenRepository repository;

  public GetUserForToken(JwtTokenRepository repository) {
    this.repository = repository;
  }

  @Override
  public UserId apply(JwtToken token) {
    return repository.validateAndGetUserId(token);
  }
}
