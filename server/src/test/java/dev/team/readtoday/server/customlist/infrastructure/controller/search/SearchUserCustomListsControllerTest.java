package dev.team.readtoday.server.customlist.infrastructure.controller.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.team.readtoday.server.customlist.application.SearchListsFromUser;
import dev.team.readtoday.server.customlist.domain.CustomList;
import dev.team.readtoday.server.customlist.domain.CustomListMother;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseControllerBridge;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.user.domain.UserMother;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
public class SearchUserCustomListsControllerTest {

  @Test
  void shouldReturnOkIfUserHasCustomList() {
    SearchListsFromUser searchListsFromUser = mock(SearchListsFromUser.class);
    SearchUserCustomListsController controller = new SearchUserCustomListsController(
        searchListsFromUser);
    SecurityContext securityContext = mock(SecurityContext.class);
    BaseControllerBridge.setSecurityContext(controller, securityContext);

    // User auth.
    User user = UserMother.random();
    UserId userId = user.getId();
    when(securityContext.getUserPrincipal()).thenReturn(userId::toString);

    Collection<CustomList> customLists = Arrays.asList(
        CustomListMother.withUserIdAndRandomPublications(userId),
        CustomListMother.withUserIdAndRandomPublications(userId)
    );

    when(searchListsFromUser.search(userId)).thenReturn(customLists);

    Response response = controller.searchUserCustomLists();
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
  }
}
