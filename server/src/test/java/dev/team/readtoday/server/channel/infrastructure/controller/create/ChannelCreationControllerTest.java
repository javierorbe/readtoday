package dev.team.readtoday.server.channel.infrastructure.controller.create;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.team.readtoday.server.channel.application.create.CreateChannel;
import dev.team.readtoday.server.channel.domain.ChannelMother;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseControllerBridge;
import dev.team.readtoday.server.user.application.SearchUserById;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.user.domain.UserMother;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import java.net.URI;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
public class ChannelCreationControllerTest {

  @Test
  void shouldReturnOkIfAdminAndRequestValid() {

    CreateChannel createChannel = mock(CreateChannel.class);
    SearchUserById searchUserById = mock(SearchUserById.class);
    ChannelCreationController controller = new ChannelCreationController(createChannel,
        searchUserById);
    SecurityContext securityContext = mock(SecurityContext.class);
    BaseControllerBridge.setSecurityContext(controller, securityContext);

    // User auth.
    User user = UserMother.randomAdmin();
    UserId userId = user.getId();
    when(searchUserById.search(userId)).thenReturn(user);
    when(securityContext.getUserPrincipal()).thenReturn(userId::toString);

    ChannelCreationRequest request = ChannelCreationRequestMother.random();
    when(createChannel.create(any(), any(), any(), any(), any()))
        .thenReturn(ChannelMother.random());

    UriInfo uriInfo = mock(UriInfo.class);
    controller.setUriInfo(uriInfo);
    UriBuilder uriBuilder = mock(UriBuilder.class);
    when(uriInfo.getAbsolutePathBuilder()).thenReturn(uriBuilder);
    when(uriBuilder.build()).thenReturn(URI.create("/channels"));


    Response response = controller.createChannel(request);
    assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
  }

  @Test
  void shouldReturnForbiddenIfNotAdmin() {

    CreateChannel createChannel = mock(CreateChannel.class);
    SearchUserById searchUserById = mock(SearchUserById.class);
    ChannelCreationController controller = new ChannelCreationController(createChannel,
        searchUserById);
    SecurityContext securityContext = mock(SecurityContext.class);
    BaseControllerBridge.setSecurityContext(controller, securityContext);

    // User auth.
    User user = UserMother.random();
    UserId userId = user.getId();
    when(searchUserById.search(userId)).thenReturn(user);
    when(securityContext.getUserPrincipal()).thenReturn(userId::toString);

    ChannelCreationRequest request = ChannelCreationRequestMother.random();

    Response response = controller.createChannel(request);
    assertEquals(Status.FORBIDDEN.getStatusCode(), response.getStatus());
  }
}
