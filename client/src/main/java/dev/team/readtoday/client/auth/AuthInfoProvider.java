package dev.team.readtoday.client.auth;

public interface AuthInfoProvider {

  /**
   * Returns the auth process being executed.
   */
  AuthProcess getAuthProcess();

  /**
   * Returns the username introduced by the user for signing up.
   *
   * @throws IllegalStateException if invoked without being in the sign up process
   */
  String getUsername();
}
