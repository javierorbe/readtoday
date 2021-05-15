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
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
public final class SearchListsFromUserTest {

  @Test
  void shouldReturnListsFromUser() {
    CustomListRepository repository = mock(CustomListRepository.class);

    CustomListId customListId = CustomListId.random();
    CustomListId customListId1 = CustomListId.random();
    CustomListTitle customListTitle = new CustomListTitle("Title");
    UserId userId = UserId.random();
    Publication publication = PublicationMother.random();
    Collection<PublicationId> publicationIds = new ArrayList<>();
    publicationIds.add(publication.getId());

    Collection<CustomList> expectedCustomLists = new ArrayList<>();
    expectedCustomLists.add(new CustomList(customListId, customListTitle, userId, publicationIds));
    expectedCustomLists.add(new CustomList(customListId1, customListTitle, userId, publicationIds));

    when(repository.getListsFromUser(userId)).thenReturn(expectedCustomLists);
    SearchListsFromUser searchListsFromUser = new SearchListsFromUser(repository);
    Collection<CustomList> actualCustomLists = searchListsFromUser.search(userId);

    assertEquals(expectedCustomLists,actualCustomLists);

  }

}
