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
import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.domain.CategoryName;
import dev.team.readtoday.server.category.domain.CategoryRepository;
import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelMother;
import dev.team.readtoday.server.channel.domain.ChannelRepository;
import dev.team.readtoday.server.channel.domain.ChannelTitle;
import dev.team.readtoday.server.shared.domain.CategoryId;
import dev.team.readtoday.server.shared.domain.ChannelId;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.shared.infrastructure.controller.AcceptanceTestAppContext;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseAcceptanceTest;
import dev.team.readtoday.server.shared.infrastructure.controller.authfilter.JwtTokenManager;
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
import java.security.SecureRandom;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public final class SearchChannelFeature extends BaseAcceptanceTest {

  private AcceptanceTestAppContext context;

  private UserRepository userRepository;
  private ChannelRepository channelRepository;
  private CategoryRepository categoryRepository;

  private UserId userId;
  private String userJwtToken;
  private Response response;
  private final Map<ChannelId, Channel> channelCache = new HashMap<>();

  @Before
  public void setUp() {
    context = new AcceptanceTestAppContext();

    clearRepositories();

    userRepository = context.getBean(UserRepository.class);
    channelRepository = context.getBean(ChannelRepository.class);
    categoryRepository = context.getBean(CategoryRepository.class);

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

  @And("there are these channels:")
  public void thereAreTheseChannels(List<? extends Map<String, String>> data) {
    for (Map<String, String> channelData : data) {
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
    userJwtToken = context.getJwtTokenManager().getForUserId(userId.toString());
  }

  @Given("I have an invalid authentication token")
  public void iHaveAnInvalidAuthenticationToken() {
    Random random = new SecureRandom();
    byte[] secret = new byte[64];
    random.nextBytes(secret);
    JwtTokenManager otherTokenManager = new JwtTokenManager(Algorithm.HMAC256(secret));
    userJwtToken = otherTokenManager.getForUserId(userId.toString());
  }

  @When("I request to search channels with the category {string}")
  public void iRequestToSearchForTheCategoryTechnology(String categoryName) {
    Client client = ClientBuilder.newClient();
    WebTarget baseTarget = client.target(getServerBaseUri());
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
  public void theResponseContentShouldHave(List<Map<String, String>> expectedChannels) {
    String jsonBody = response.readEntity(String.class);
    JsonObject responseContent = new Gson().fromJson(jsonBody, JsonObject.class);

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
