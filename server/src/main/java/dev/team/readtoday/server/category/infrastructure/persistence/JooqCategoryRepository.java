package dev.team.readtoday.server.category.infrastructure.persistence;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CATEGORY;

import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.domain.CategoryName;
import dev.team.readtoday.server.category.domain.CategoryRepository;
import dev.team.readtoday.server.shared.domain.CategoryId;
import dev.team.readtoday.server.shared.domain.Service;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Record2;

@Service
public final class JooqCategoryRepository implements CategoryRepository {

  private final DSLContext dsl;

  public JooqCategoryRepository(DSLContext dsl) {
    this.dsl = dsl;
  }

  @Override
  public void save(Category category) {
    dsl.insertInto(CATEGORY, CATEGORY.ID, CATEGORY.NAME)
        .values(category.getId().toString(), category.getName().toString()).onDuplicateKeyIgnore()
        .execute();
  }

  @Override
  public Collection<Category> getById(Collection<CategoryId> ids) {
    var result =
        dsl.select(CATEGORY.ID, CATEGORY.NAME).from(CATEGORY).where(CATEGORY.ID.in(ids)).fetch();

    return result.stream().map(record -> {
      CategoryId id = CategoryId.fromString(record.get(CATEGORY.ID));
      CategoryName name = new CategoryName(record.get(CATEGORY.NAME));
      return new Category(id, name);
    }).collect(Collectors.toUnmodifiableSet());
  }

  @Override
  public Set<Category> getAll() {
    var result = dsl.select(CATEGORY.ID, CATEGORY.NAME).from(CATEGORY).fetch();

    return result.stream().map(this::createCategoryFromResult)
        .collect(Collectors.toUnmodifiableSet());
  }

  @Override
  public Optional<Category> getByName(CategoryName categoryName) {
    Record1<String> record = dsl.select(CATEGORY.ID).from(CATEGORY)
        .where(CATEGORY.NAME.equalIgnoreCase(categoryName.toString())).fetchOne();

    if (record == null) {
      return Optional.empty();
    }

    CategoryId categoryId = CategoryId.fromString(record.value1());
    return Optional.of(new Category(categoryId, categoryName));
  }

  private Category createCategoryFromResult(Record2<String, String> result) {
    CategoryId id = CategoryId.fromString(result.getValue(CATEGORY.ID));
    CategoryName name = new CategoryName(result.getValue(CATEGORY.NAME));
    return new Category(id, name);
  }
}
