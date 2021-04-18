package dev.team.readtoday.server.channel.infrastructure.controller.create;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CATEGORY;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CHANNEL;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CHANNEL_CATEGORIES;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.shared.infrastructure.controller.AcceptanceTestAppContext;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseAcceptanceTest;
import dev.team.readtoday.server.user.domain.EmailAddress;
import dev.team.readtoday.server.user.domain.Role;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.user.domain.UserRepository;
import dev.team.readtoday.server.user.domain.Username;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class CreateChannelFeature extends BaseAcceptanceTest {

  private AcceptanceTestAppContext context;

  private UserRepository userRepository;

  private String userJwtToken;
  private Response response;

  @Before
  public void setUp() {
    context = new AcceptanceTestAppContext();
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
    context.clearTables(CHANNEL_CATEGORIES, CATEGORY, CHANNEL, USER);
  }

  @Given("there are these users:")
  public void thereAreTheseUsers(List<Map<String, String>> dataList) {
    dataList.forEach(this::createUserFromData);
  }

  private void createUserFromData(Map<String, String> data) {
    UserId id = UserId.fromString(data.get("userId"));
    Username username = new Username(data.get("username"));
    EmailAddress email = new EmailAddress(data.get("email"));
    Role role = Role.valueOf(data.get("role"));
    User user = new User(id, username, email, role);
    userRepository.save(user);
  }

  @Given("I have a valid authentication token for the user with ID {string}")
  public void iHaveAValidAuthenticationTokenForTheUserWithId(String userIdStr) {
    userJwtToken = context.getJwtTokenManager().getForUserId(userIdStr);
  }

  @Given("I have an invalid authentication token")
  public void iHaveAnInvalidAuthenticationToken() {
    userJwtToken =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
  }

  @When("I request to create a channel with the following characteristics:")
  public void iRequestToCreateAChannelWithTheFollowingCharacteristics(Map<String, String> data) {
    Client client = ClientBuilder.newClient();
    WebTarget baseTarget = client.target(getServerBaseUri());
    WebTarget channelsTarget = baseTarget.path("channels");

    TestChannelCreationRequest request = buildRequestFromData(data);

    response = channelsTarget
        .request(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userJwtToken)
        .post(Entity.entity(request, MediaType.APPLICATION_JSON));
  }

  private static TestChannelCreationRequest buildRequestFromData(Map<String, String> data) {
    String title = data.get("title");
    String rssUrl = data.get("rssUrl");
    String description = data.get("description");
    String imageUrl = data.get("imageUrl");

    return new TestChannelCreationRequest(
        title,
        rssUrl,
        description,
        imageUrl,
        Set.of()
    );
  }

  @Then("the response status code should be {int}")
  public void theResponseStatusCodeShouldBe(int statusCode) {
    assertEquals(statusCode, response.getStatus());
  }
}
