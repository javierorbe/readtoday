package dev.team.readtoday.server.category.infrastructure;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CATEGORY;

import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.domain.CategoryId;
import dev.team.readtoday.server.category.domain.CategoryName;
import dev.team.readtoday.server.category.domain.CategoryRepository;
import java.util.Optional;
import org.jooq.DSLContext;
import org.jooq.Record2;

public class JooqCategoryRepository implements CategoryRepository {

  private final DSLContext dsl;

  public JooqCategoryRepository(DSLContext dsl) {
    this.dsl = dsl;
  }

  @Override
  public void save(Category category) {
    dsl.insertInto(CATEGORY, CATEGORY.ID, CATEGORY.NAME)
        .values(
            category.getId().toString(),
            category.getName().toString()
        ).execute();
  }

  @Override
  public Optional<Category> getById(CategoryId id) {
    Record2<String, String> record =
        dsl.select(CATEGORY.ID, CATEGORY.NAME)
            .from(CATEGORY)
            .where(CATEGORY.ID.eq(id.toString()))
            .fetchOne();

    if (record == null) {
      return Optional.empty();
    }

    CategoryName name = new CategoryName(record.getValue(CATEGORY.NAME));

    return Optional.of(new Category(id, name));
  }


}
