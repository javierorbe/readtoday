package dev.team.readtoday.server.jwt.domain;

import dev.team.readtoday.server.shared.domain.StringValueObject;

public final class JwtToken extends StringValueObject {

  private JwtToken(String value) {
    super(value);
  }

  public static JwtToken fromString(String value) {
    return new JwtToken(value);
  }
}
