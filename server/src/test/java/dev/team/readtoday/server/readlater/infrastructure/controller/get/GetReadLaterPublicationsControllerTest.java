package dev.team.readtoday.server.readlater.infrastructure.controller.get;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.team.readtoday.server.publication.application.get.GetPublication;
import dev.team.readtoday.server.readlater.application.SearchReadLaterList;
import dev.team.readtoday.server.readlater.domain.ReadLaterList;
import dev.team.readtoday.server.shared.domain.PublicationId;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseControllerBridge;
import dev.team.readtoday.server.user.domain.NonExistingUser;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.user.domain.UserMother;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
public final class GetReadLaterPublicationsControllerTest {

  @Test
  void shouldReturnOk() throws NonExistingUser {
    SearchReadLaterList searchReadLaterList = mock(SearchReadLaterList.class);
    GetPublication getPublication = mock(GetPublication.class);

    GetReadLaterPublicationsController controller =
        new GetReadLaterPublicationsController(searchReadLaterList, getPublication);
    SecurityContext securityContext = mock(SecurityContext.class);
    BaseControllerBridge.setSecurityContext(controller, securityContext);

    User user = UserMother.random();
    UserId userId = user.getId();
    Collection<PublicationId> publications = new ArrayList<>();
    publications.add(mock(PublicationId.class));
    ReadLaterList list = new ReadLaterList(userId, publications);
    when(securityContext.getUserPrincipal()).thenReturn(userId::toString);
    Response response = mock(Response.class);
    when(searchReadLaterList.search(userId)).thenReturn(list);
    response = controller.getPublications();
    assertEquals(Status.OK.getStatusCode(), response.getStatus());

  }
}
