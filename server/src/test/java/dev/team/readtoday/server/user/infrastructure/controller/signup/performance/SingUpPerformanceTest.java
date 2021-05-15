package dev.team.readtoday.server.user.infrastructure.controller.signup.performance;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.USER;
import static org.mockito.Mockito.mock;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.shared.infrastructure.PerformanceTest;
import dev.team.readtoday.server.shared.infrastructure.controller.AcceptanceTestAppContext;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseAcceptanceTest;
import dev.team.readtoday.server.user.application.profile.AccessToken;
import dev.team.readtoday.server.user.application.profile.ProfileFetcher;
import dev.team.readtoday.server.user.domain.EmailAddress;
import dev.team.readtoday.server.user.domain.EmailAddressMother;
import dev.team.readtoday.server.user.domain.Role;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.user.domain.UsernameMother;
import dev.team.readtoday.server.user.infrastructure.controller.signup.TestSignUpRequest;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Category(PerformanceTest.class)
public class SingUpPerformanceTest extends BaseAcceptanceTest {

  @Rule
  public ContiPerfRule rule = new ContiPerfRule();

  private AcceptanceTestAppContext context;
  private ProfileFetcher fetch;

  @Test
  @PerfTest(invocations = 50)
  public void SingUpPerfTest() {
    fetch = mock(ProfileFetcher.class);
    context = new AcceptanceTestAppContext(fetch);
    initServer(context);

    AccessToken token = new AccessToken("someToken");
    EmailAddress currentEmail = EmailAddressMother.random();
    User user = new User(UserId.random(), UsernameMother.random(), currentEmail, Role.USER);

    Client client = ClientBuilder.newClient();
    WebTarget base = client.target(getServerBaseUri());
    WebTarget signUpTarget = base.path("auth/signup");

    TestSignUpRequest request =
        new TestSignUpRequest(token.toString(), user.getUsername().toString());

    Response response = signUpTarget.request(MediaType.APPLICATION_JSON)
        .post(Entity.entity(request, MediaType.APPLICATION_JSON));

    closeServer();
    context.clearTables(USER);
    context.close();
  }
}
