package dev.team.readtoday.server.user.infrastructure.controller.signup;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.shared.infrastructure.controller.AcceptanceTestAppContext;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseAcceptanceTest;
import dev.team.readtoday.server.user.application.AccessToken;
import dev.team.readtoday.server.user.application.AuthProcessFailed;
import dev.team.readtoday.server.user.application.ProfileFetcher;
import dev.team.readtoday.server.user.domain.EmailAddress;
import dev.team.readtoday.server.user.domain.Role;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.user.domain.UserRepository;
import dev.team.readtoday.server.user.domain.Username;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Map;

public final class SignUpControllerTest extends BaseAcceptanceTest {

  private AcceptanceTestAppContext context;

  private UserRepository userRepository;
  private ProfileFetcher profileFetcher;

  private final AccessToken accessToken = new AccessToken("someAccessToken");
  private EmailAddress currentEmail;

  private Response response;

  @Before
  public void setUp() {
    profileFetcher = mock(ProfileFetcher.class);
    context = new AcceptanceTestAppContext(profileFetcher);

    clearRepositories();

    userRepository = context.getBean(UserRepository.class);

    initServer(context);
  }

  @After
  public void tearDown() {
    clearRepositories();
    closeServer();
    context.close();
  }

  private void clearRepositories() {
    context.clearTables(USER);
  }

  @Given("there is a user:")
  public void thereIsAUser(Map<String, String> data) {
    UserId id = UserId.fromString(data.get("userId"));
    Username username = new Username(data.get("username"));
    EmailAddress email = new EmailAddress(data.get("email"));
    User user = new User(id, username, email, Role.USER);
    userRepository.save(user);
  }

  @Given("my email address is {string}")
  public void myEmailAddressIs(String emailStr) {
    currentEmail = new EmailAddress(emailStr);
  }

  @And("I have a valid access token")
  public void iHaveAValidAccessToken() throws AuthProcessFailed {
    when(profileFetcher.fetchEmailAddress(eq(accessToken)))
        .thenReturn(currentEmail);
    when(profileFetcher.fetchEmailAddress(not(eq(accessToken))))
        .thenThrow(AuthProcessFailed.class);
  }

  @And("I have an invalid access token")
  public void iHaveAnInvalidAccessToken() throws AuthProcessFailed {
    when(profileFetcher.fetchEmailAddress(any()))
        .thenThrow(AuthProcessFailed.class);
  }

  @When("I request to sign up as {string}")
  public void iRequestToSignUpAs(String username) {
    Client client = ClientBuilder.newClient();
    WebTarget baseTarget = client.target(getServerBaseUri());
    WebTarget signUpTarget = baseTarget.path("auth/signup");

    TestSignUpRequest request = new TestSignUpRequest(accessToken.toString(), username);

    response = signUpTarget
        .request(MediaType.APPLICATION_JSON)
        .post(Entity.entity(request, MediaType.APPLICATION_JSON));
  }

  @Then("the response status code should be {int}")
  public void theResponseStatusCodeShouldBe(int statusCode) {
    assertEquals(statusCode, response.getStatus());
  }
}
