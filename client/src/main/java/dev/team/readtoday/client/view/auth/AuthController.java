package dev.team.readtoday.client.view.auth;

public interface AuthController {

  /**
   * Send sign up request to server.
   *
   * @param googleOauthToken Google OAuth 2.0 token
   * @param username user's sign up username
   * @return JWT auth token
   */
  String signUp(String googleOauthToken, String username);

  /**
   * Send sign in request to server.
   *
   * @param googleOauthToken Google OAuth 2.0 token
   * @return JWT auth token
   */
  String signIn(String googleOauthToken);
}
