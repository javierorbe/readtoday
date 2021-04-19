package dev.team.readtoday.server.jwt.domain;

import dev.team.readtoday.server.shared.domain.UserId;

public interface JwtTokenRepository {

  JwtToken getForUserId(UserId userId);

  UserId validateAndGetUserId(JwtToken token);
}
