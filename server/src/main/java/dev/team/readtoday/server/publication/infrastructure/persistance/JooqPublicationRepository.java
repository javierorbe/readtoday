package dev.team.readtoday.server.publication.infrastructure.persistance;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CATEGORY;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.PUBLICATION;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.PUBLICATION_CATEGORIES;

import dev.team.readtoday.server.publication.domain.Publication;
import dev.team.readtoday.server.publication.domain.PublicationDate;
import dev.team.readtoday.server.publication.domain.PublicationDescription;
import dev.team.readtoday.server.publication.domain.PublicationLink;
import dev.team.readtoday.server.publication.domain.PublicationRepository;
import dev.team.readtoday.server.publication.domain.PublicationTitle;
import dev.team.readtoday.server.shared.domain.CategoryId;
import dev.team.readtoday.server.shared.domain.PublicationId;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record5;
import org.jooq.impl.DSL;

public class JooqPublicationRepository implements PublicationRepository {

  private final DSLContext dsl;

  public JooqPublicationRepository(DSLContext dsl) {
    this.dsl = dsl;
  }

  @Override
  public void save(Publication publication) {
    dsl.transaction(configuration -> {
      savePublication(configuration, publication);
      savePublicationCategories(configuration, publication);
    });
  }

  @Override
  public void remove(Publication publication) {
    dsl.deleteFrom(PUBLICATION).where(PUBLICATION.ID.eq(publication.getId().toString())).execute();
  }

  @Override
  public Optional<Publication> getFromId(PublicationId id) {
    Record5<String, String, String, String, String> result =
        dsl.select(PUBLICATION.ID, PUBLICATION.TITLE, PUBLICATION.DESCRIP, PUBLICATION.DATE,
            PUBLICATION.LINK).from(PUBLICATION).where(PUBLICATION.ID.eq(id.toString())).fetchOne();

    if (result == null) {
      return Optional.empty();
    }

    Publication publication = createPublicationFromResult(result);
    return Optional.of(publication);
  }

  private static void savePublication(Configuration configuration, Publication publication) {
    DSL.using(configuration)
        .insertInto(PUBLICATION, PUBLICATION.ID, PUBLICATION.TITLE, PUBLICATION.DESCRIP,
            PUBLICATION.DATE, PUBLICATION.LINK)
        .values(publication.getId().toString(), publication.getTitle().get().toString(),
            publication.getDescription().get().toString(),
            publication.getDate().get().getDateTime().toString(),
            publication.getLink().get().toString())
        .onDuplicateKeyIgnore().execute();
  }

  private static void savePublicationCategories(Configuration configuration,
      Publication publication) {

    var step = DSL.using(configuration).insertInto(PUBLICATION_CATEGORIES,
        PUBLICATION_CATEGORIES.PUBLICATION_ID, PUBLICATION_CATEGORIES.CATEGORY_ID);

    String id = publication.getId().toString();

    for (CategoryId categoryId : publication.getCategories()) {
      step.values(id, categoryId.toString());
    }

    step.onDuplicateKeyIgnore().execute();

  }

  private Publication createPublicationFromResult(
      Record5<String, String, String, String, String> result) {
    PublicationId id = new PublicationId(result.getValue(PUBLICATION.ID));
    PublicationTitle title = new PublicationTitle(result.getValue(PUBLICATION.TITLE));
    PublicationDescription desc = new PublicationDescription(result.getValue(PUBLICATION.DESCRIP));
    PublicationDate date =
        new PublicationDate(LocalDateTime.parse(result.getValue(PUBLICATION.DATE)));
    PublicationLink link = new PublicationLink(result.getValue(PUBLICATION.LINK));

    Collection<CategoryId> categories = getCategoriesFromPublicaiton(id);

    return new Publication(id, title, desc, date, link, categories);
  }

  private Collection<CategoryId> getCategoriesFromPublicaiton(PublicationId id) {
    var result = dsl.select(CATEGORY.ID).from(CATEGORY).join(PUBLICATION_CATEGORIES)
        .on(CATEGORY.ID.eq(PUBLICATION_CATEGORIES.CATEGORY_ID))
        .where(PUBLICATION_CATEGORIES.PUBLICATION_ID.eq(id.toString())).fetch();
    return result.stream().map(record -> CategoryId.fromString(record.value1()))
        .collect(Collectors.toSet());
  }
}
