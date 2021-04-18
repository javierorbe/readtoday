package dev.team.readtoday.server.jwt.application.get;

import dev.team.readtoday.server.jwt.domain.JwtToken;
import dev.team.readtoday.server.jwt.domain.JwtTokenRepository;
import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.UserId;
import java.util.function.Function;

@Service
public final class GetJwtToken implements Function<UserId, JwtToken> {

  private final JwtTokenRepository repository;

  public GetJwtToken(JwtTokenRepository repository) {
    this.repository = repository;
  }

  @Override
  public JwtToken apply(UserId userId) {
    return repository.getForUserId(userId);
  }
}
