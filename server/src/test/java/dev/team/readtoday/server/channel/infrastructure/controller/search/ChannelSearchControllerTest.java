package dev.team.readtoday.server.channel.infrastructure.controller.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.javafaker.Faker;
import dev.team.readtoday.server.category.application.search.SearchCategory;
import dev.team.readtoday.server.category.domain.CategoryName;
import dev.team.readtoday.server.channel.application.SearchChannelsByCategory;
import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelMother;
import jakarta.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
public class ChannelSearchControllerTest {

  private static final Faker faker = Faker.instance();

  @Test
  void shouldReturnOk() {
    String name = faker.bothify("Category Name ?????");
    CategoryName categoryName = new CategoryName(name);
    SearchChannelsByCategory searchChannelsByCategory = mock(SearchChannelsByCategory.class);
    SearchCategory searchCategory = mock(SearchCategory.class);
    ChannelSearchController controller = new ChannelSearchController(
        searchChannelsByCategory,
        searchCategory
    );

    Collection<Channel> channels = Arrays.asList(
        ChannelMother.random(),
        ChannelMother.random()
    );

    when(searchChannelsByCategory.search(categoryName)).thenReturn(channels);
    Response response = controller.getAllByCategoryName(categoryName);

    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

  }
}
