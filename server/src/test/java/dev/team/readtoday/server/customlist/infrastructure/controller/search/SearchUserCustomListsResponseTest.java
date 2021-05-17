package dev.team.readtoday.server.customlist.infrastructure.controller.search;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
public class SearchUserCustomListsResponseTest {

  @Test
  void shouldGetCorrectly() {
    Collection<CustomListResponse> customListResponses = Arrays.asList(
        CustomListResponseMother.random(),
        CustomListResponseMother.random()
    );

    SearchUserCustomListsResponse response = new SearchUserCustomListsResponse(
        customListResponses
    );

    assertEquals(customListResponses, response.getLists());
  }
}
