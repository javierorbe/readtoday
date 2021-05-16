package dev.team.readtoday.server.customlist.infrastructure.controller.add;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.team.readtoday.server.readlater.application.AddPublication;
import dev.team.readtoday.server.shared.domain.PublicationId;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseControllerBridge;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.user.domain.UserMother;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
public class CustomListAddControllerTest {

  @Test
  void shouldReturnOk() {
    AddPublication addPublication = mock(AddPublication.class);
    CustomListAddController controller = new CustomListAddController(addPublication);
    SecurityContext securityContext = mock(SecurityContext.class);
    BaseControllerBridge.setSecurityContext(controller, securityContext);

    // User auth.
    User user = UserMother.randomAdmin();
    UserId userId = user.getId();
    when(securityContext.getUserPrincipal()).thenReturn(userId::toString);

    CustomListAddRequest request = new CustomListAddRequest();
    PublicationId publicationId = PublicationId.random();
    request.setPublicationId(publicationId.toString());

    assertEquals(publicationId.toString(), request.getPublicationId());

    Response response = controller.addPublicationToCustomList(request);

    assertEquals(Status.OK.getStatusCode(), response.getStatus());
  }
}
