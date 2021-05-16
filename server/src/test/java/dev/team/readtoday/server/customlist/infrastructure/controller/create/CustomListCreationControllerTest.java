package dev.team.readtoday.server.customlist.infrastructure.controller.create;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.team.readtoday.server.customlist.application.CreateCustomListCommand;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.shared.domain.bus.command.CommandBus;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseControllerBridge;
import dev.team.readtoday.server.user.application.SearchUserById;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.user.domain.UserMother;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;

@TestMethodOrder(MethodOrderer.Random.class)
public class CustomListCreationControllerTest {

  @Test
  void shouldReturnOkIfSucceeded() {
    CommandBus commandBus = mock(CommandBus.class);
    SearchUserById searchUserById = mock(SearchUserById.class);

    CustomListCreationController controller = new CustomListCreationController(commandBus,
        searchUserById);
    SecurityContext securityContext = mock(SecurityContext.class);
    BaseControllerBridge.setSecurityContext(controller, securityContext);

    // User auth.
    User user = UserMother.random();
    UserId userId = user.getId();
    when(securityContext.getUserPrincipal()).thenReturn(userId::toString);
    when(searchUserById.search(userId)).thenReturn(user);

    // Request creation
    CustomListCreationRequest request = new CustomListCreationRequest();
    String title = "testTitle123";
    request.setTitle(title);

    Response response = controller.createCustomList(request);

    var commandCaptor = ArgumentCaptor.forClass(CreateCustomListCommand.class);
    verify(commandBus).dispatch(commandCaptor.capture());
    CreateCustomListCommand command = commandCaptor.getValue();

    assertEquals(title, command.getTitle());
    assertEquals(userId.toString(), command.getUserId());

    assertEquals(Status.OK.getStatusCode(), response.getStatus());
  }
}
