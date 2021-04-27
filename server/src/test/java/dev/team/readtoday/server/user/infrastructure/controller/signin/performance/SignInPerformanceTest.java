package dev.team.readtoday.server.user.infrastructure.controller.signin.performance;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.junit.Test;
import dev.team.readtoday.server.shared.infrastructure.controller.AcceptanceTestAppContext;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseAcceptanceTest;
import dev.team.readtoday.server.user.infrastructure.controller.signin.SignInRequest;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class SignInPerformanceTest extends BaseAcceptanceTest {
  
  @Rule
  public ContiPerfRule rule = new ContiPerfRule();

  private AcceptanceTestAppContext context;

  @Test
  @PerfTest(invocations = 50)
  public void signInPerfTest() {
    context = new AcceptanceTestAppContext();
    initServer(context);

    Response response;
    Client client = ClientBuilder.newClient();
    WebTarget base = client.target(getServerBaseUri());
    WebTarget signInTarget = base.path("auth/signin");

    SignInRequest request = new SignInRequest();
    request.setAccessToken("someToken");

    response = signInTarget.request(MediaType.APPLICATION_JSON)
        .post(Entity.entity(request, MediaType.APPLICATION_JSON));
    closeServer();
    context.close();
  }
}
