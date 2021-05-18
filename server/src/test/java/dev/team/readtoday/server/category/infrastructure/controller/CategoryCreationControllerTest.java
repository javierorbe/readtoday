package dev.team.readtoday.server.category.infrastructure.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.team.readtoday.server.category.application.CreateCategory;
import dev.team.readtoday.server.category.infrastructure.controller.create.CategoryCreationController;
import dev.team.readtoday.server.category.infrastructure.controller.create.CategoryCreationRequest;
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
public class CategoryCreationControllerTest {

  @Test
  void shouldReturnCreatedIfAdmin() {
    CategoryCreationRequest request = CategoryCreationRequestMother.random();
    CreateCategory createCategory = mock(CreateCategory.class);
    SearchUserById searchUserById = mock(SearchUserById.class);
    CategoryCreationController controller = new CategoryCreationController(
        createCategory,
        searchUserById
    );

    SecurityContext securityContext = mock(SecurityContext.class);
    BaseControllerBridge.setSecurityContext(controller, securityContext);

    // User auth.
    User user = UserMother.randomAdmin();
    UserId userId = user.getId();
    when(securityContext.getUserPrincipal()).thenReturn(userId::toString);
    when(searchUserById.search(userId)).thenReturn(user);

    UriInfo uriInfo = mock(UriInfo.class);
    UriBuilder uriBuilder = mock(UriBuilder.class);
    when(uriInfo.getAbsolutePathBuilder()).thenReturn(uriBuilder);
    when(uriBuilder.build()).thenReturn(URI.create("/category"));
    controller.setUriInfo(uriInfo);

    Response response = controller.createCategory(request);
    assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
  }

  @Test
  void shouldReturnForbiddenIfNotAdmin() {
    CategoryCreationRequest request = CategoryCreationRequestMother.random();
    CreateCategory createCategory = mock(CreateCategory.class);
    SearchUserById searchUserById = mock(SearchUserById.class);
    CategoryCreationController controller = new CategoryCreationController(
        createCategory,
        searchUserById
    );

    SecurityContext securityContext = mock(SecurityContext.class);
    BaseControllerBridge.setSecurityContext(controller, securityContext);

    // User auth.
    User user = UserMother.random();
    UserId userId = user.getId();
    when(securityContext.getUserPrincipal()).thenReturn(userId::toString);
    when(searchUserById.search(userId)).thenReturn(user);

    Response response = controller.createCategory(request);
    assertEquals(Status.FORBIDDEN.getStatusCode(), response.getStatus());
  }
}
