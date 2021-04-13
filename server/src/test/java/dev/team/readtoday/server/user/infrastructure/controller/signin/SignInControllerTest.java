package dev.team.readtoday.server.user.infrastructure.controller.signin;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.zaxxer.hikari.HikariConfig;
import dev.team.readtoday.server.channel.infrastructure.controller.search.ChannelSearchControllerTest;
import dev.team.readtoday.server.shared.infrastructure.controller.JwtTokenManager;
import dev.team.readtoday.server.shared.infrastructure.persistence.JooqConnectionBuilder;
import dev.team.readtoday.server.user.application.AuthProcessFailed;
import dev.team.readtoday.server.user.application.AccessToken;
import dev.team.readtoday.server.user.application.ProfileFetcher;
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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public final class SignInControllerTest {

  private static final String CONFIG_FILE = "/config.json";
  private static final Gson GSON = new Gson();

  private String baseUri;
  private HttpServer server;
  private JooqConnectionBuilder jooq;

  private final JwtTokenManager jwtTokenManager =
      new JwtTokenManager(Algorithm.HMAC256("sup3rs3cr3t"));

  private UserRepository userRepository;
  private ProfileFetcher profileFetcher;

  private final AccessToken accessToken = new AccessToken("someAccessToken");
  private EmailAddress currentEmail;

  private Response response;

  @Before
  public void setUp() throws FileNotFoundException {
    jooq = new JooqConnectionBuilder(new HikariConfig("/datasource.properties"));
    clearRepositories();

    userRepository = new JooqUserRepository(jooq.getContext());

    profileFetcher = mock(ProfileFetcher.class);

    ResourceConfig jerseyConfig =
        new SignInTestingJerseyConfig(jwtTokenManager, userRepository, profileFetcher);
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

  @When("I request to sign in")
  public void iRequestToSignIn() {
    Client client = ClientBuilder.newClient();
    WebTarget baseTarget = client.target(baseUri);
    WebTarget signInTarget = baseTarget.path("auth/signin");

    TestSignInRequest request = new TestSignInRequest(accessToken.toString());

    response = signInTarget
        .request(MediaType.APPLICATION_JSON)
        .post(Entity.entity(request, MediaType.APPLICATION_JSON));
  }

  @Then("the response status code should be {int}")
  public void theResponseStatusCodeShouldBe(int statusCode) {
    assertEquals(statusCode, response.getStatus());
  }
}
