package dev.team.readtoday.server.user.infrastructure.controller.signin.performance;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.USER;
import static org.mockito.Mockito.mock;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
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
import dev.team.readtoday.server.user.domain.UserRepository;
import dev.team.readtoday.server.user.domain.UsernameMother;
import dev.team.readtoday.server.user.infrastructure.controller.signin.TestSignInRequest;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RunWith(JUnit4.class)
@Category(PerformanceTest.class)
public class SignInPerformanceTest extends BaseAcceptanceTest {

  @Rule
  public ContiPerfRule rule = new ContiPerfRule();

  private AcceptanceTestAppContext context;
  private UserRepository userRepository;
  private ProfileFetcher fetch;

  @Test
  @PerfTest(invocations = 50)
  public void signInPerfTest() {
    fetch = mock(ProfileFetcher.class);
    context = new AcceptanceTestAppContext(fetch);
    initServer(context);
    userRepository = context.getBean(UserRepository.class);

    AccessToken token = new AccessToken("someToken");
    EmailAddress currentEmail = EmailAddressMother.random();
    User user = new User(UserId.random(), UsernameMother.random(), currentEmail, Role.USER);

    userRepository.save(user);

    Response response;
    Client client = ClientBuilder.newClient();
    WebTarget base = client.target(getServerBaseUri());
    WebTarget signInTarget = base.path("auth/signin");

    TestSignInRequest request = new TestSignInRequest(token.toString());

    response = signInTarget.request(MediaType.APPLICATION_JSON)
        .post(Entity.entity(request, MediaType.APPLICATION_JSON));

    closeServer();
    context.clearTables(USER);
    context.close();
  }
}
