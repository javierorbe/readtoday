package dev.team.readtoday.server.readlater.infrastructure.persistence;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.READLATER;

import dev.team.readtoday.server.readlater.domain.ReadLaterList;
import dev.team.readtoday.server.readlater.domain.ReadLaterListRespository;
import dev.team.readtoday.server.shared.domain.PublicationId;
import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.UserId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.Result;

@Service
public class JooqReadLaterRepository implements ReadLaterListRespository {

  private final DSLContext dsl;

  public JooqReadLaterRepository(DSLContext dsl) {
    this.dsl = dsl;
  }

  @Override
  public Optional<ReadLaterList> getByUserId(UserId userId) {
    Result<Record2<String, String>> result = dsl.select(READLATER.USER_ID, READLATER.PUBLICATION_ID)
        .from(READLATER).where(READLATER.USER_ID.eq(userId.toString())).fetch();

    List<PublicationId> publicationIds = new ArrayList<>();

    for (Record2<String, String> results : result) {
      PublicationId publicationId =
          PublicationId.fromString(results.getValue(READLATER.PUBLICATION_ID));
      publicationIds.add(publicationId);
    }
    ReadLaterList readLaterList = new ReadLaterList(userId, publicationIds);
    return Optional.of(readLaterList);
  }

  @Override
  public void addPublication(UserId userId, PublicationId publicationId) {
    dsl.insertInto(READLATER, READLATER.USER_ID, READLATER.PUBLICATION_ID)
        .values(userId.toString(), publicationId.toString()).onDuplicateKeyIgnore().execute();
  }


}
