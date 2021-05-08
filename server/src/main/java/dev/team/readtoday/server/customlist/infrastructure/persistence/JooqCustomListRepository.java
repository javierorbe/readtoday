package dev.team.readtoday.server.customlist.infrastructure.persistence;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CUSTOM_LIST;

import dev.team.readtoday.server.customlist.domain.CustomList;
import dev.team.readtoday.server.customlist.domain.CustomListRepository;
import dev.team.readtoday.server.shared.domain.Service;
import org.jooq.DSLContext;

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
}
