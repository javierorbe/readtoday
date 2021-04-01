package dev.team.readtoday.client.oauth;

public interface JwtTokenReceiver {

  void receiveJwtToken(String jwtToken);
}
