package dev.team.readtoday.server.subscription.infrastructure.controller.get;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.team.readtoday.server.category.application.search.SearchCategory;
import dev.team.readtoday.server.channel.application.SearchChannelsFromSubscriptions;
import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.shared.domain.PublicationId;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseControllerBridge;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.user.domain.UserMother;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.Test;

public class SubscriptionGetControllerTest {
  /*
  @Test
  void shouldReturnOkIfSucceeded(){
    SearchChannelsFromSubscriptions searchChannelsFromSubscriptions = mock(SearchChannelsFromSubscriptions.class);
    SearchCategory searchCategory = mock(SearchCategory.class);

    SubscriptionGetController controller = new SubscriptionGetController(searchChannelsFromSubscriptions, searchCategory);
    SecurityContext securityContext = mock(SecurityContext.class);
    BaseControllerBridge.setSecurityContext(controller, securityContext);

    User user = UserMother.random();
    UserId userId = user.getId();
    when(securityContext.getUserPrincipal()).thenReturn(userId::toString);

    Collection<Channel> channels = new ArrayList<>();
    channels.add(mock(Channel.class));
    when(searchChannelsFromSubscriptions.search(userId)).thenReturn(channels);

    Response response = mock(Response.class);
    response = controller.getSubscription();
    assertEquals(Status.OK.getStatusCode(), response.getStatus());
  }*/
}
