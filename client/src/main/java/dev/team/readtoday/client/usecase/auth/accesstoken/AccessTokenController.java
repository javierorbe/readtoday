package dev.team.readtoday.client.usecase.auth.accesstoken;

import org.greenrobot.eventbus.EventBus;
import dev.team.readtoday.client.usecase.auth.AuthInfoProvider;
import dev.team.readtoday.client.usecase.auth.AuthProcess;
import dev.team.readtoday.client.usecase.auth.signin.SignInRequestReadyEvent;
import dev.team.readtoday.client.usecase.auth.signup.SignUpRequestReadyEvent;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

@Path("/")
public final class AccessTokenController {

  private final Map<AuthProcess, Consumer<String>> eventDispatcherMap = new EnumMap<>(Map.of(
      AuthProcess.SIGN_IN, this::postSignInRequestReadyEvent,
      AuthProcess.SIGN_UP, this::postSignUpRequestReadyEvent
  ));

  @Inject
  private EventBus eventBus;

  @Inject
  private AuthInfoProvider authInfoProvider;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String receiveAccessToken(@QueryParam("code") String accessToken) {
    try {
      postReadyEvent(accessToken);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "OK! Check the app.";
  }

  private void postReadyEvent(String accessToken) {
    AuthProcess authProcess = Objects.requireNonNull(authInfoProvider.getAuthProcess());
    eventDispatcherMap.get(authProcess).accept(accessToken);
  }

  private void postSignUpRequestReadyEvent(String accessToken) {
    String username = authInfoProvider.getUsername();
    eventBus.post(new SignUpRequestReadyEvent(accessToken, username));
  }

  private void postSignInRequestReadyEvent(String accessToken) {
    eventBus.post(new SignInRequestReadyEvent(accessToken));
  }
}
