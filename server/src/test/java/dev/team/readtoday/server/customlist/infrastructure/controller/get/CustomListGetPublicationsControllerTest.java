package dev.team.readtoday.server.customlist.infrastructure.controller.get;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.team.readtoday.server.customlist.application.SearchCustomList;
import dev.team.readtoday.server.customlist.domain.CustomList;
import dev.team.readtoday.server.customlist.domain.CustomListMother;
import dev.team.readtoday.server.publication.application.get.GetPublication;
import dev.team.readtoday.server.publication.domain.PublicationMother;
import dev.team.readtoday.server.shared.domain.CustomListId;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.Optional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
public class CustomListGetPublicationsControllerTest {

  @Test
  void shouldReturnOk() throws Exception {

    // Mock applications
    SearchCustomList searchCustomList = mock(SearchCustomList.class);
    GetPublication getPublication = mock(GetPublication.class);

    // This should not be executed because of random custom list doesn't have publications.
    when(getPublication.get(any())).thenReturn(Optional.of(PublicationMother.random()));

    // Mock search + create request
    CustomListGetPublicationsRequest request = new CustomListGetPublicationsRequest();
    CustomList customList = CustomListMother.withRandomPublications();
    when(searchCustomList.search(customList.getId())).thenReturn(Optional.of(customList));
    request.setCustomListId(customList.getId().toString());

    // Mock controller
    CustomListGetPublicationsController controller = new CustomListGetPublicationsController(
        searchCustomList, getPublication);

    // Catch response
    Response response = controller.getPublicationsOfCustomList(request);

    // Verify status
    assertEquals(Status.OK.getStatusCode(), response.getStatus());
  }

  @Test
  void shouldReturnNotFound() throws Exception {

    // Mock applications
    SearchCustomList searchCustomList = mock(SearchCustomList.class);
    GetPublication getPublication = mock(GetPublication.class);

    // Mock search + create request
    CustomListGetPublicationsRequest request = new CustomListGetPublicationsRequest();
    CustomListId customListId = CustomListId.random();
    when(searchCustomList.search(customListId)).thenReturn(Optional.empty());
    request.setCustomListId(customListId.toString());

    // Mock controller + Catch response
    CustomListGetPublicationsController controller = new CustomListGetPublicationsController(
        searchCustomList, getPublication);

    Response response = controller.getPublicationsOfCustomList(request);

    // Verify status
    assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
  }

  @Test
  void shouldReturnBadRequest() throws Exception {
    // Mock applications
    SearchCustomList searchCustomList = mock(SearchCustomList.class);
    GetPublication getPublication = mock(GetPublication.class);

    // Mock search + create request
    CustomListGetPublicationsRequest request = new CustomListGetPublicationsRequest();
    when(searchCustomList.search(any())).thenThrow(Exception.class);

    // Mock controller
    CustomListGetPublicationsController controller = new CustomListGetPublicationsController(
        searchCustomList, getPublication);

    // Catch response
    Response response = controller.getPublicationsOfCustomList(request);

    // Verify status
    assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
  }
}
