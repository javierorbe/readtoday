package dev.team.readtoday.server.user.infrastructure.oauth;

import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.team.readtoday.server.user.application.profile.AccessToken;
import dev.team.readtoday.server.user.application.profile.ProfileFetchingFailed;
import dev.team.readtoday.server.user.application.profile.ProfileFetcher;
import dev.team.readtoday.server.user.domain.EmailAddress;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutionException;

public final class GoogleProfileFetcher implements ProfileFetcher {

  private static final Gson GSON = new Gson();

  private static final String GOOGLE_USERINFO_URL =
      "https://www.googleapis.com/oauth2/v1/userinfo?alt=json";

  private final OAuth20Service service;

  /**
   * Create a Google profile fetcher given the Google OAuth app credentials.
   *
   * @param clientId Google OAuth 2.0 client ID
   * @param clientSecret Google OAuth 2.0 client secret
   * @param redirect determines how Google's authorization server sends a response to the app
   * @see <a href="https://developers.google.com/identity/protocols/oauth2/native-app#step-2:-send-a-request-to-googles-oauth-2.0-server">Send
   * a request to Google's OAuth 2.0 Server - Google Developers</a>
   */
  public GoogleProfileFetcher(String clientId, String clientSecret, URI redirect) {
    service = new ServiceBuilder(clientId)
        .apiSecret(clientSecret)
        .callback(redirect.toString())
        .build(GoogleApi20.instance());
  }

  @Override
  public EmailAddress fetchEmailAddress(AccessToken token) throws ProfileFetchingFailed {
    try {
      OAuth2AccessToken accessToken = service.getAccessToken(token.toString());

      OAuthRequest request = new OAuthRequest(Verb.GET, GOOGLE_USERINFO_URL);
      service.signRequest(accessToken, request);

      Response response = service.execute(request);

      JsonElement jsonElement = GSON.fromJson(response.getBody(), JsonElement.class);
      JsonObject jsonObj = jsonElement.getAsJsonObject();
      String email = jsonObj.get("email").getAsString();

      return new EmailAddress(email);
    } catch (IOException | InterruptedException | ExecutionException e) {
      throw new ProfileFetchingFailed("Email fetching failed.", e);
    }
  }
}
