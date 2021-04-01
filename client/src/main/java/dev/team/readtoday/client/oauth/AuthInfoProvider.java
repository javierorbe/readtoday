package dev.team.readtoday.client.oauth;

public interface AuthInfoProvider {

  AuthProcess getAuthProcess();

  String getUsername();
}
