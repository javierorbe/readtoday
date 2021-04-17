package dev.team.readtoday.server.publication.infrastructure.controller.channel;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CATEGORY;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CHANNEL;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CHANNEL_CATEGORIES;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelMother;
import dev.team.readtoday.server.channel.domain.ChannelRepository;
import dev.team.readtoday.server.channel.domain.ChannelTitle;
import dev.team.readtoday.server.channel.domain.RssUrl;
import dev.team.readtoday.server.shared.domain.ChannelId;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.shared.infrastructure.controller.AcceptanceTestAppContext;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseAcceptanceTest;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.user.domain.UserMother;
import dev.team.readtoday.server.user.domain.UserRepository;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

public final class GetChannelPublicationsFeature extends BaseAcceptanceTest {

  private AcceptanceTestAppContext context;

  private UserRepository userRepository;
  private ChannelRepository channelRepository;

  private UserId userId;
  private String userJwtToken;
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
    context.clearTables(CHANNEL_CATEGORIES, CATEGORY, CHANNEL, USER);
  }

  @Given("there is a user with ID {string}")
  public void thereIsAUser(String userIdStr) {
    userId = UserId.fromString(userIdStr);
    User user = UserMother.withId(userId);
    userRepository.save(user);
  }


  @And("there are is a channel:")
  public void thereAreIsAChannel(Map<String, String> data) {
    ChannelId id = ChannelId.fromString(data.get("id"));
    ChannelTitle title = new ChannelTitle(data.get("title"));
    RssUrl rssUrl = getTestRssUrl();
    Channel channel = ChannelMother.withIdTitleAndRssUrl(id, title, rssUrl);
    channelRepository.save(channel);
  }

  private static RssUrl getTestRssUrl() {
    URL url = GetChannelPublicationsFeature.class.getResource(
        "/dev/team/readtoday/server/publication/infrastructure/controller/channel/nytimes-rss.xml");
    return new RssUrl(url.toString());
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

  @Then("the response status code should be {int}")
  public void theResponseStatusCodeShouldBe(int statusCode) {
    assertEquals(statusCode, response.getStatus());
  }

  @When("I request to the publications of the channel with ID {string}")
  public void iRequestToThePublicationsOfTheChannelWithId(String channelId) {
    Client client = ClientBuilder.newClient();
    WebTarget baseTarget = client.target(getServerBaseUri());
    WebTarget publicationsTarget = baseTarget.path("publications");

    response = publicationsTarget
        .queryParam("channelId", channelId)
        .request(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userJwtToken)
        .get();
  }

  @And("the response content should be:")
  public void theResponseContentShouldBe(String expectedContentStr) {
    String responseEntity = response.readEntity(String.class);
    Gson gson = new Gson();
    JsonArray actualContent = gson.fromJson(responseEntity, JsonArray.class);
    JsonArray expectedContent = gson.fromJson(expectedContentStr, JsonArray.class);

    for (int i = 0; i < expectedContent.size(); ++i) {
      JsonObject expectedPublication = expectedContent.get(i).getAsJsonObject();
      JsonObject actualPublication = actualContent.get(i).getAsJsonObject();

      for (Entry<String, JsonElement> entry : expectedPublication.entrySet()) {
        assertEquals(expectedPublication.get(entry.getKey()), actualPublication.get(entry.getKey()));
      }
    }
  }
}
