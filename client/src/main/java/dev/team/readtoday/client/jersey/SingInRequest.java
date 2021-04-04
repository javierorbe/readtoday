package dev.team.readtoday.client.jersey;

public final class SingInRequest {

  private final String token;

  SingInRequest(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }
}
