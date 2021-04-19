package dev.team.readtoday.server.subscription.infrastructure.controller.post;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CHANNEL;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.SUBSCRIPTION;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelDescription;
import dev.team.readtoday.server.channel.domain.ChannelRepository;
import dev.team.readtoday.server.channel.domain.ChannelTitle;
import dev.team.readtoday.server.channel.domain.ImageUrl;
import dev.team.readtoday.server.channel.domain.RssUrl;
import dev.team.readtoday.server.jwt.domain.JwtToken;
import dev.team.readtoday.server.jwt.domain.JwtTokenMother;
import dev.team.readtoday.server.shared.domain.ChannelId;
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
import io.cucumber.java.en.And;
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
import java.util.Map;
import java.util.Set;

public final class SubscribeFeature extends BaseAcceptanceTest {

  private AcceptanceTestAppContext context;

  private UserRepository userRepository;
  private ChannelRepository channelRepository;

  private JwtToken userJwtToken;
  private Response response;

  @Before
  public void setUp() {
    context = new AcceptanceTestAppContext();

    clearRepositories();

    userRepository = context.getBean(UserRepository.class);
    channelRepository = context.getBean(ChannelRepository.class);

    initServer(context);
  }

  @After
  public void tearDown() {
    clearRepositories();
    closeServer();
    context.close();
  }

  private void clearRepositories() {
    context.clearTables(SUBSCRIPTION, CHANNEL, USER);
  }

  @Given("there is a user:")
  public void thereIsAUser(Map<String, String> data) {
    UserId id = UserId.fromString(data.get("userId"));
    Username username = new Username(data.get("username"));
    EmailAddress email = new EmailAddress(data.get("email"));
    User user = new User(id, username, email, Role.USER);
    userRepository.save(user);
  }

  @Given("I have a valid authentication token for the user with ID {string}")
  public void iHaveAValidAuthenticationTokenForTheUserWithId(String userIdStr) {
    userJwtToken = context.getJwtTokenForUser(userIdStr);
  }

  @Given("I have an invalid authentication token")
  public void iHaveAnInvalidAuthenticationToken() {
    userJwtToken = JwtTokenMother.random();
  }

  @And("there is a channel:")
  public void thereIsAChannel(Map<String, String> data) {
    ChannelId id = ChannelId.fromString(data.get("channelId"));
    ChannelTitle title = new ChannelTitle(data.get("title"));
    RssUrl rssUrl = RssUrl.create(data.get("rssUrl"));
    ChannelDescription description = new ChannelDescription(data.get("description"));
    ImageUrl imageUrl = ImageUrl.create(data.get("imageUrl"));
    channelRepository.save(new Channel(
        id,
        title,
        rssUrl,
        description,
        imageUrl,
        Set.of()
    ));
  }

  @When("I request to subscribe to the channel with ID {string}")
  public void iRequestToSubscribeToTheChannelWithId(String channelIdStr) {
    Client client = ClientBuilder.newClient();
    WebTarget baseTarget = client.target(getServerBaseUri());
    WebTarget subscriptionTarget = baseTarget.path("subscriptions");

    response = subscriptionTarget
        .path(channelIdStr)
        .request(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userJwtToken)
        .post(Entity.json(null));
  }

  @Then("the response status code should be {int}")
  public void theResponseStatusCodeShouldBe(int statusCode) {
    assertEquals(statusCode, response.getStatus());
  }
}
