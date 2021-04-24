package dev.team.readtoday.server.channel.infrastructure.controller.edit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.team.readtoday.server.channel.application.edit.EditChannelCommand;
import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelMother;
import dev.team.readtoday.server.shared.domain.ChannelId;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.shared.domain.bus.command.CommandBus;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseControllerBridge;
import dev.team.readtoday.server.user.application.SearchUserById;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.user.domain.UserMother;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;
import java.util.List;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;

@TestMethodOrder(MethodOrderer.Random.class)
public final class ChannelEditControllerTest {

  @Test
  void shouldReturnOkIfSucceeded() {
    CommandBus commandBus = mock(CommandBus.class);
    SearchUserById searchUserById = mock(SearchUserById.class);

    ChannelEditController controller = new ChannelEditController(commandBus, searchUserById);
    SecurityContext securityContext = mock(SecurityContext.class);
    BaseControllerBridge.setSecurityContext(controller, securityContext);

    // User auth.
    User user = UserMother.randomAdmin();
    UserId userId = user.getId();
    when(securityContext.getUserPrincipal()).thenReturn(userId::toString);
    when(searchUserById.search(userId)).thenReturn(user);

    // Channel creation
    ChannelId channelId = ChannelId.random();
    Channel channel = ChannelMother.withId(channelId);

    // Request creation
    ChannelEditRequest request = new ChannelEditRequest();
    request.setTitle(channel.getTitle().toString());
    request.setDescription(channel.getDescription().toString());
    request.setRssUrl(channel.getRssUrl().toString());
    request.setImageUrl(channel.getImageUrl().toString());
    request.setCategoryIds(List.of());

    Response response = controller.editChannel(request, channelId.toString());

    var commandCaptor = ArgumentCaptor.forClass(EditChannelCommand.class);
    verify(commandBus).dispatch(commandCaptor.capture());
    EditChannelCommand command = commandCaptor.getValue();

    assertEquals(channelId.toString(), command.getId());
    assertEquals(request.getTitle(), command.getTitle());
    assertEquals(request.getDescription(), command.getDescription());
    assertEquals(request.getRssUrl(), command.getRssUrl());
    assertEquals(request.getImageUrl(), command.getImageUrl());
    assertEquals(request.getCategoryIds(), command.getCategories());

    assertEquals(Status.OK.getStatusCode(), response.getStatus());
  }
}
