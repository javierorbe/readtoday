package dev.team.readtoday.server.readlater.infrastructure.controller.create;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.team.readtoday.server.publication.application.create.CreatePublication;
import dev.team.readtoday.server.publication.application.get.GetPublication;
import dev.team.readtoday.server.publication.domain.PublicationDate;
import dev.team.readtoday.server.publication.domain.PublicationDescription;
import dev.team.readtoday.server.publication.domain.PublicationLink;
import dev.team.readtoday.server.publication.domain.PublicationTitle;
import dev.team.readtoday.server.readlater.application.AddPublication;
import dev.team.readtoday.server.shared.domain.CategoryId;
import dev.team.readtoday.server.shared.domain.PublicationId;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseControllerBridge;
import dev.team.readtoday.server.shared.infrastructure.jooq.tables.PublicationCategories;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.user.domain.UserMother;
import jakarta.ws.rs.core.SecurityContext;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;

@TestMethodOrder(MethodOrderer.Random.class)
public class CreatePublicationReadLaterControllerTest {

  @Test
  void shouldReturnOk() {
    GetPublication getPublication = mock(GetPublication.class);
    CreatePublication createPublication = mock(CreatePublication.class);
    AddPublication addPublication = mock(AddPublication.class);
    CreatePublicationReadLaterController controller = new CreatePublicationReadLaterController(
        getPublication,
        createPublication,
        addPublication
    );
    SecurityContext securityContext = mock(SecurityContext.class);
    BaseControllerBridge.setSecurityContext(controller, securityContext);

    // User auth.
    User user = UserMother.random();
    UserId userId = user.getId();
    when(securityContext.getUserPrincipal()).thenReturn(userId::toString);

    PublicationRequest request = new PublicationRequest();

    // Create request ...
    request.setId(UUID.randomUUID().toString());
    request.setTitle("añlskdjfñlaksjdf");
    request.setLink("http://ljdalkjdfa.com");
    request.setDescription("Hello there!");
    request.setDate(OffsetDateTime.now());
    request.setCategories(Collections.emptyList());

    controller.savePublication(request);
    when(getPublication.get(any())).thenReturn(Optional.empty());
    ArgumentCaptor<PublicationId> idCaptor = ArgumentCaptor.forClass(PublicationId.class);
    ArgumentCaptor<PublicationTitle> titleCaptor = ArgumentCaptor.forClass(PublicationTitle.class);
    ArgumentCaptor<PublicationLink> linkCaptor = ArgumentCaptor.forClass(PublicationLink.class);
    ArgumentCaptor<PublicationDescription> descriptionCaptor = ArgumentCaptor.forClass(PublicationDescription.class);
    ArgumentCaptor<PublicationDate> dateCaptor = ArgumentCaptor.forClass(PublicationDate.class);
    ArgumentCaptor<Collection<CategoryId>> categoriesCaptor = ArgumentCaptor.forClass(Collection.class);

    verify(createPublication).create(idCaptor.capture(),
        titleCaptor.capture(),
        descriptionCaptor.capture(),
        dateCaptor.capture(),
        linkCaptor.capture(),
        categoriesCaptor.capture());

    doNothing().when(addPublication).add(any(), any());

    PublicationId originalId = PublicationId.fromString(request.getId());
    PublicationTitle originalTitle = new PublicationTitle(request.getTitle());
    PublicationLink originalLink = new PublicationLink(request.getLink());
    PublicationDescription originalDescription = new PublicationDescription(
        request.getDescription());
    PublicationDate originalDate = new PublicationDate(request.getDate());
    Collection<CategoryId> originalCategories = request.getCategories().stream()
        .map(CategoryId::fromString).collect(
            Collectors.toSet());

    assertEquals(originalId, idCaptor.getValue());
    assertEquals(originalTitle, titleCaptor.getValue());
    assertEquals(originalLink, linkCaptor.getValue());
    assertEquals(originalDescription, descriptionCaptor.getValue());
    assertEquals(originalDate, dateCaptor.getValue());
    assertEquals(originalCategories, categoriesCaptor.getValue());
  }
}
