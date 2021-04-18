package dev.team.readtoday.server.jwt.domain;

public enum JwtTokenMother {
  ;

  private static final String A_JWT_TOKEN =
      "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

  public static JwtToken random() {
    return JwtToken.fromString(A_JWT_TOKEN);
  }
}
