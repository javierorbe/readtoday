package dev.team.readtoday.server.jwt.infrastructure;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.team.readtoday.server.jwt.domain.InvalidJwtToken;
import dev.team.readtoday.server.jwt.domain.JwtToken;
import dev.team.readtoday.server.jwt.domain.JwtTokenRepository;
import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.UserId;
import org.springframework.beans.factory.annotation.Qualifier;

@Service
public final class Auth0JwtTokenRepository implements JwtTokenRepository {

  private final Algorithm algorithm;

  public Auth0JwtTokenRepository(@Qualifier("jwtSigningAlg") Algorithm algorithm) {
    this.algorithm = algorithm;
  }

  @Override
  public JwtToken getForUserId(UserId userId) {
    String token = JWT.create().withClaim("user_id", userId.toString()).sign(algorithm);
    return JwtToken.fromString(token);
  }

  @Override
  public UserId validateAndGetUserId(JwtToken token) {
    try {
      JWTVerifier verifier = JWT.require(algorithm).build();
      DecodedJWT jwt = verifier.verify(token.toString());
      String userId = jwt.getClaim("user_id").asString();
      return UserId.fromString(userId);
    } catch (JWTVerificationException e) {
      throw new InvalidJwtToken(e);
    }
  }
}
