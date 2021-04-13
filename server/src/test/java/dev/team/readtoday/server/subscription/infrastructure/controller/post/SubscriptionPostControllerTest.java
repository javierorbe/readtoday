package dev.team.readtoday.server.subscription.infrastructure.controller.post;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CHANNEL;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.SUBSCRIPTION;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.zaxxer.hikari.HikariConfig;
import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelDescription;
import dev.team.readtoday.server.shared.domain.ChannelId;
import dev.team.readtoday.server.channel.domain.ChannelRepository;
import dev.team.readtoday.server.channel.domain.ChannelTitle;
import dev.team.readtoday.server.channel.domain.ImageUrl;
import dev.team.readtoday.server.channel.domain.RssUrl;
import dev.team.readtoday.server.channel.infrastructure.controller.search.ChannelSearchControllerTest;
import dev.team.readtoday.server.channel.infrastructure.persistence.JooqChannelRepository;
import dev.team.readtoday.server.shared.infrastructure.controller.JwtTokenManager;
import dev.team.readtoday.server.shared.infrastructure.persistence.JooqConnectionBuilder;
import dev.team.readtoday.server.subscription.domain.SubscriptionRepository;
import dev.team.readtoday.server.subscription.infrastructure.persistence.JooqSubscriptionRepository;
import dev.team.readtoday.server.user.domain.EmailAddress;
import dev.team.readtoday.server.user.domain.Role;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.user.domain.UserRepository;
import dev.team.readtoday.server.user.domain.Username;
import dev.team.readtoday.server.user.infrastructure.persistence.JooqUserRepository;
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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public final class SubscriptionPostControllerTest {

  private static final String CONFIG_FILE = "/config.json";
  private static final Gson GSON = new Gson();

  private String baseUri;
  private HttpServer server;
  private JooqConnectionBuilder jooq;

  private JwtTokenManager jwtTokenManager;

  private UserRepository userRepository;
  private ChannelRepository channelRepository;

  private String userJwtToken;
  private Response response;

  @Before
  public void setUp() throws FileNotFoundException {
    jooq = new JooqConnectionBuilder(new HikariConfig("/datasource.properties"));
    clearRepositories();

    userRepository = new JooqUserRepository(jooq.getContext());
    channelRepository = new JooqChannelRepository(jooq.getContext());
    SubscriptionRepository subRepository = new JooqSubscriptionRepository(jooq.getContext());

    jwtTokenManager = new JwtTokenManager(Algorithm.HMAC256("sup3rs3cr3t"));

    ResourceConfig jerseyConfig =
        new SubscriptionPostTestingJerseyConfig(jwtTokenManager, userRepository, subRepository);
    initServer(jerseyConfig);
  }

  @After
  public void tearDown() {
    clearRepositories();
    jooq.close();
    server.shutdownNow();
  }

  private void initServer(ResourceConfig jerseyConfig) throws FileNotFoundException {
    JsonObject config = loadConfig();
    baseUri = config.get("baseUri").getAsString();
    server = GrizzlyHttpServerFactory.createHttpServer(URI.create(baseUri), jerseyConfig);
  }

  private void clearRepositories() {
    jooq.getContext().deleteFrom(SUBSCRIPTION).execute();
    jooq.getContext().deleteFrom(CHANNEL).execute();
    jooq.getContext().deleteFrom(USER).execute();
  }

  private static JsonObject loadConfig() throws FileNotFoundException {
    URL fileUrl =
        Objects.requireNonNull(ChannelSearchControllerTest.class.getResource(CONFIG_FILE));
    String file = fileUrl.getFile();
    return GSON.fromJson(new JsonReader(new FileReader(file)), JsonObject.class);
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
    userJwtToken = jwtTokenManager.getForUserId(userIdStr);
  }

  @Given("I have an invalid authentication token")
  public void iHaveAnInvalidAuthenticationToken() {
    userJwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
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
  public void iRequestToSubscribeToTheChannelWithID(String channelIdStr) {
    Client client = ClientBuilder.newClient();
    WebTarget baseTarget = client.target(baseUri);
    WebTarget subscriptionTarget = baseTarget.path("subscriptions");

    TestSubscriptionPostRequest request = new TestSubscriptionPostRequest(channelIdStr);

    response = subscriptionTarget
        .request(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userJwtToken)
        .post(Entity.entity(request, MediaType.APPLICATION_JSON));
  }

  @Then("the response status code should be {int}")
  public void theResponseStatusCodeShouldBe(int statusCode) {
    assertEquals(statusCode, response.getStatus());
  }
}
