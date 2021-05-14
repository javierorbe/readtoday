package dev.team.readtoday.server.customlist.infrastructure.persistence;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CUSTOM_LIST;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CUSTOM_LIST_PUBLICATIONS;

import dev.team.readtoday.server.customlist.domain.CustomList;
import dev.team.readtoday.server.customlist.domain.CustomListRepository;
import dev.team.readtoday.server.customlist.domain.CustomListTitle;
import dev.team.readtoday.server.shared.domain.CustomListId;
import dev.team.readtoday.server.shared.domain.PublicationId;
import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.UserId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.Result;

@Service
public class JooqCustomListRepository implements CustomListRepository {

  private final DSLContext dsl;

  public JooqCustomListRepository(DSLContext dsl) {
    this.dsl = dsl;
  }

  @Override
  public void save(CustomList customList) {
    dsl.insertInto(CUSTOM_LIST, CUSTOM_LIST.ID, CUSTOM_LIST.TITLE, CUSTOM_LIST.USER_ID)
        .values(
            customList.getId().toString(),
            customList.getTitle().toString(),
            customList.getUserId().toString()
        )
        .onDuplicateKeyUpdate()
        .set(CUSTOM_LIST.TITLE, customList.getTitle().toString())
        .execute();
  }


  @Override
  public void addPublication(CustomListId customListId,
      PublicationId publicationId) {
    dsl.insertInto(CUSTOM_LIST_PUBLICATIONS, CUSTOM_LIST_PUBLICATIONS.CUSTOM_LIST_ID,
        CUSTOM_LIST_PUBLICATIONS.PUBLICATION_ID)
        .values(customListId.toString(), publicationId.toString())
        .onDuplicateKeyIgnore().execute();
  }

  @Override
  public Optional<CustomList> getFromId(
      CustomListId customListId) {
    Record3<String, String, String> result =
        dsl.select(CUSTOM_LIST.ID, CUSTOM_LIST.TITLE, CUSTOM_LIST.USER_ID).from(CUSTOM_LIST)
            .where(CUSTOM_LIST.ID.eq(customListId.toString())).fetchOne();

    if (result == null) {
      return Optional.empty();
    }

    CustomList customList = createCustomListFromResult(result);
    return Optional.of(customList);
  }

  @Override
  public List<PublicationId> getPublications(CustomListId customListId) {
    Result<Record2<String, String>> result =
        dsl.select(CUSTOM_LIST_PUBLICATIONS.CUSTOM_LIST_ID, CUSTOM_LIST_PUBLICATIONS.PUBLICATION_ID)
            .from(CUSTOM_LIST_PUBLICATIONS)
            .where(CUSTOM_LIST_PUBLICATIONS.CUSTOM_LIST_ID.eq(customListId.toString())).fetch();

    List<PublicationId> publicationIds = new ArrayList<>();

    for (Record2<String, String> results : result) {
      PublicationId publicationId = PublicationId
          .fromString(results.getValue(CUSTOM_LIST_PUBLICATIONS.PUBLICATION_ID));
      publicationIds.add(publicationId);
    }

    return publicationIds;
  }

  @Override
  public Collection<CustomList> getListsFromUser(UserId userId) {
    var result =
        dsl.select(CUSTOM_LIST.ID, CUSTOM_LIST.TITLE, CUSTOM_LIST.USER_ID)
            .from(CUSTOM_LIST)
            .where(CUSTOM_LIST.USER_ID.eq(userId.toString())).fetch();

    return result.stream().map(this::createCustomListFromResult).collect(Collectors.toSet());
  }

  private CustomList createCustomListFromResult(Record3<String, String, String> result) {
    CustomListId customListId = CustomListId.fromString(result.getValue(CUSTOM_LIST.ID));
    CustomListTitle customListTitle = new CustomListTitle(result.getValue(CUSTOM_LIST.TITLE));
    UserId userId = UserId.fromString(result.getValue(CUSTOM_LIST.ID));

    return new CustomList(customListId, customListTitle, userId, getPublications(customListId));
  }


}
