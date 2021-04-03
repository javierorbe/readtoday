package dev.team.readtoday.server.channel.infrastructure.controller.search;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CATEGORY;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CHANNEL;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CHANNEL_CATEGORIES;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.zaxxer.hikari.HikariConfig;
import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.domain.CategoryName;
import dev.team.readtoday.server.category.domain.CategoryRepository;
import dev.team.readtoday.server.category.infrastructure.persistence.JooqCategoryRepository;
import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelId;
import dev.team.readtoday.server.channel.domain.ChannelMother;
import dev.team.readtoday.server.channel.domain.ChannelRepository;
import dev.team.readtoday.server.channel.domain.ChannelTitle;
import dev.team.readtoday.server.channel.infrastructure.persistence.JooqChannelRepository;
import dev.team.readtoday.server.shared.domain.CategoryId;
import dev.team.readtoday.server.shared.infrastructure.controller.JwtTokenManager;
import dev.team.readtoday.server.shared.infrastructure.persistence.JooqConnectionBuilder;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.user.domain.UserId;
import dev.team.readtoday.server.user.domain.UserMother;
import dev.team.readtoday.server.user.domain.UserRepository;
import dev.team.readtoday.server.user.infrastructure.persistence.JooqUserRepository;
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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public final class ChannelSearchControllerTest {

  private static final String CONFIG_FILE = "/config.json";
  private static final Gson GSON = new Gson();

  private String baseUri;
  private HttpServer server;
  private JooqConnectionBuilder jooq;
  private UserRepository userRepository;
  private ChannelRepository channelRepository;
  private CategoryRepository categoryRepository;
  private JwtTokenManager jwtTokenManager;

  private UserId userId;
  private String userJwtToken;
  private Response response;
  private final Map<ChannelId, Channel> channelCache = new HashMap<>();

  @Before
  public void initServer() throws FileNotFoundException {
    JsonObject config = loadConfig();
    baseUri = config.get("baseUri").getAsString();

    jwtTokenManager = new JwtTokenManager(Algorithm.HMAC256("sup3rs3cr3t"));

    jooq = new JooqConnectionBuilder(new HikariConfig("/datasource.properties"));
    userRepository = new JooqUserRepository(jooq.getContext());
    channelRepository = new JooqChannelRepository(jooq.getContext());
    categoryRepository = new JooqCategoryRepository(jooq.getContext());

    jooq.getContext().deleteFrom(CHANNEL_CATEGORIES).execute();
    jooq.getContext().deleteFrom(CATEGORY).execute();
    jooq.getContext().deleteFrom(CHANNEL).execute();
    jooq.getContext().deleteFrom(USER).execute();

    ResourceConfig jerseyConfig = new TestingJerseyConfig(jwtTokenManager, userRepository,
        channelRepository, categoryRepository);

    server = GrizzlyHttpServerFactory.createHttpServer(URI.create(baseUri), jerseyConfig);
  }

  @After
  public void stopServer() {
    jooq.close();
    server.shutdownNow();
  }

  private static JsonObject loadConfig() throws FileNotFoundException {
    URL fileUrl =
        Objects.requireNonNull(ChannelSearchControllerTest.class.getResource(CONFIG_FILE));
    String file = fileUrl.getFile();
    return GSON.fromJson(new JsonReader(new FileReader(file)), JsonObject.class);
  }

  @Given("there is a user with ID {string}")
  public void thereIsAUser(String userIdStr) {
    userId = UserId.fromString(userIdStr);
    User user = UserMother.withId(userId);
    userRepository.save(user);
  }

  @And("there are these channels:")
  public void thereAreTheseChannels(List<? extends Map<String, String>> data) {
    for (Map<String, String> channelData : data) {
      System.out.println("CHANNEL DATA: " + channelData.size());
      ChannelId id = ChannelId.fromString(channelData.get("channelId"));
      ChannelTitle title = new ChannelTitle(channelData.get("title"));
      Channel channel = ChannelMother.withIdAndTitle(id, title);
      channelRepository.save(channel);
      channelCache.put(id, channel);
    }
  }

  @And("those channels have these categories:")
  public void thoseChannelsHaveTheseCategories(List<Map<String, String>> data) {
    for (Map<String, String> categoryData : data) {
      ChannelId channelId = ChannelId.fromString(categoryData.get("channelId"));
      CategoryId categoryId = CategoryId.fromString(categoryData.get("categoryId"));
      CategoryName categoryName = new CategoryName(categoryData.get("categoryName"));

      Category category = new Category(categoryId, categoryName);
      categoryRepository.save(category);

      Channel channel = channelCache.get(channelId);
      channel.addCategory(categoryId);
      channelRepository.save(channel);
    }
  }

  @Given("I have a valid authentication token")
  public void iHaveAValidAuthenticationToken() {
    userJwtToken = jwtTokenManager.getForUserId(userId.toString());
  }

  @When("I request to search channels with the category {string}")
  public void iRequestToSearchForTheCategoryTechnology(String categoryName) {
    Client client = ClientBuilder.newClient();
    WebTarget baseTarget = client.target(baseUri);
    WebTarget subscriptionTarget = baseTarget.path("channels");

    response = subscriptionTarget
        .queryParam("categoryName", categoryName)
        .request(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userJwtToken)
        .get();
  }

  @Then("the response status code should be {int}")
  public void theResponseStatusCodeShouldBe(int statusCode) {
    assertEquals(statusCode, response.getStatus());
  }

  @And("the response content should have:")
  public void theResponseContentShouldBe(List<Map<String, String>> expectedChannels) {
    String jsonBody = response.readEntity(String.class);
    JsonObject responseContent = GSON.fromJson(jsonBody, JsonObject.class);

    Collection<String> expectedChannelIds = expectedChannels.stream()
        .map(channelData -> channelData.get("id"))
        .collect(Collectors.toUnmodifiableSet());

    JsonArray channels = responseContent.get("channels").getAsJsonArray();

    for (JsonElement channelElem : channels) {
      JsonObject channel = channelElem.getAsJsonObject();
      String channelId = channel.get("id").getAsString();
      assertTrue(expectedChannelIds.contains(channelId));
    }
  }
}
