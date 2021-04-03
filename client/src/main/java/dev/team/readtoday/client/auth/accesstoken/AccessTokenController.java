package dev.team.readtoday.client.auth.accesstoken;

import com.google.common.eventbus.EventBus;
import dev.team.readtoday.client.auth.AuthInfoProvider;
import dev.team.readtoday.client.auth.SignInRequestReadyEvent;
import dev.team.readtoday.client.auth.SignUpRequestReadyEvent;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/oauth")
public final class AccessTokenController {

  private static final Logger LOGGER = LoggerFactory.getLogger(AccessTokenController.class);

  @Inject
  private EventBus eventBus;

  @Inject
  private AuthInfoProvider authInfoProvider;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String receiveAccessToken(@QueryParam("code") String accessToken) {
    LOGGER.debug("Access token received: {}", accessToken);

    switch (authInfoProvider.getAuthProcess()) {
      case SIGN_UP -> eventBus
          .post(new SignUpRequestReadyEvent(accessToken, authInfoProvider.getUsername()));
      case SIGN_IN -> eventBus.post(new SignInRequestReadyEvent(accessToken));
    }

    return "OK! Check the app.";
  }
}
