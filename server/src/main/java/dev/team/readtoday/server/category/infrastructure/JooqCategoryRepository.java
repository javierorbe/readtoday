package dev.team.readtoday.server.category.infrastructure;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CATEGORY;

import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.domain.CategoryName;
import dev.team.readtoday.server.category.domain.CategoryRepository;
import java.util.Optional;
import org.jooq.DSLContext;
import org.jooq.Record1;

public final class JooqCategoryRepository implements CategoryRepository {

  private final DSLContext dsl;

  public JooqCategoryRepository(DSLContext dsl) {
    this.dsl = dsl;
  }

  @Override
  public void save(Category category) {
    dsl.insertInto(CATEGORY, CATEGORY.CATEGORY_NAME)
        .values(
            category.getName().toString()
        ).execute();
  }

  @Override
  public Optional<Category> getByName(CategoryName categoryName) {
    Record1<String> record =
        dsl.select(CATEGORY.CATEGORY_NAME)
            .from(CATEGORY)
            .where(CATEGORY.CATEGORY_NAME.eq(categoryName.toString()))
            .fetchOne();

    if (record == null) {
      return Optional.empty();
    }

    return Optional.of(new Category(categoryName));
  }


}
