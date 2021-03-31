package dev.team.readtoday.server.category.infrastructure.persistence;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CATEGORY;

import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.domain.CategoryName;
import dev.team.readtoday.server.category.domain.CategoryRepository;
import dev.team.readtoday.server.shared.domain.CategoryId;
import dev.team.readtoday.server.shared.infrastructure.jooq.tables.records.CategoryRecord;
import java.util.Optional;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.TableField;

public final class JooqCategoryRepository implements CategoryRepository {

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
        )
        .onDuplicateKeyIgnore()
        .execute();
  }

  @Override
  public Optional<Category> getByName(CategoryName categoryName) {

    return getBy(CATEGORY.NAME, categoryName.toString());
  }

  @Override
  public Optional<Category> getById(CategoryId categoryId) {
    return getBy(CATEGORY.ID, categoryId.toString());
  }

  private Optional<Category> getBy(TableField<CategoryRecord, String> field, String value) {
    Record2<String, String> record =
        dsl.select(CATEGORY.ID, CATEGORY.NAME)
            .from(CATEGORY)
            .where(field.eq(value))
            .fetchOne();

    if (record == null) {
      return Optional.empty();
    }

    CategoryId id = CategoryId.fromString(record.getValue(CATEGORY.ID));
    CategoryName name = new CategoryName(record.getValue(CATEGORY.NAME));

    return Optional.of(new Category(id, name));
  }
}
