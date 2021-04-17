package dev.team.readtoday.server.shared.infrastructure.controller.authfilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public final class JwtTokenManager {

  private final Algorithm algorithm;

  public JwtTokenManager(Algorithm algorithm) {
    this.algorithm = algorithm;
  }

  public String getForUserId(String userId) {
    return JWT.create()
        .withClaim("user_id", userId)
        .sign(algorithm);
  }

  String validateAndGetUserId(String token) throws InvalidJwtToken {
    try {
      JWTVerifier verifier = JWT.require(algorithm).build();
      DecodedJWT jwt = verifier.verify(token);
      return jwt.getClaim("user_id").asString();
    } catch (JWTVerificationException e) {
      throw new InvalidJwtToken(e);
    }
  }
}
