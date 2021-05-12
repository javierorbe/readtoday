package dev.team.readtoday.server.customlist.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.team.readtoday.server.customlist.domain.CustomList;
import dev.team.readtoday.server.customlist.domain.CustomListRepository;
import dev.team.readtoday.server.customlist.domain.CustomListTitle;
import dev.team.readtoday.server.publication.domain.Publication;
import dev.team.readtoday.server.publication.domain.PublicationMother;
import dev.team.readtoday.server.shared.domain.CustomListId;
import dev.team.readtoday.server.shared.domain.PublicationId;
import dev.team.readtoday.server.shared.domain.UserId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
final class SearchCustomListTest {

  @Test
  void shouldReturnList() throws Exception {
    CustomListRepository repository = mock(CustomListRepository.class);

    CustomListId customListId = CustomListId.random();
    CustomListTitle customListTitle = new CustomListTitle("Title");
    UserId userId = UserId.random();
    Publication publication = PublicationMother.random();
    Collection<PublicationId> publicationIds = new ArrayList<>();
    publicationIds.add(publication.getId());

    CustomList expectedList = new CustomList(customListId, customListTitle, userId, publicationIds);
    when(repository.getFromId(customListId)).thenReturn(Optional.of(expectedList));
    SearchCustomList searchCustomList = new SearchCustomList(repository);
    CustomList actualList = searchCustomList.search(customListId).get();

    assertEquals(expectedList, actualList);
  }
}
