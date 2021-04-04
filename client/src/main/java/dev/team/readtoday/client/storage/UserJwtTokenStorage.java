package dev.team.readtoday.client.storage;

public class UserJwtTokenStorage {

  private static String token;

  public static void setToken(String token) {
    UserJwtTokenStorage.token = token;
  }

  public static String getToken() {
    return token;
  }
}
