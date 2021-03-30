package dev.team.readtoday.server.category.infrastructure;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CATEGORY;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.zaxxer.hikari.HikariConfig;
import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.domain.CategoryMother;
import dev.team.readtoday.server.category.domain.CategoryRepository;
import dev.team.readtoday.server.shared.infrastructure.persistence.JooqConnectionBuilder;
import java.util.Optional;
import org.jooq.DSLContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;


@TestMethodOrder(MethodOrderer.Random.class)
@Tag("IntegrationTest")
final class JooqCategoryRepositoryTest {

  private static JooqConnectionBuilder jooq;
  private static CategoryRepository repository;

  @BeforeAll
  static void setup() {
    jooq = new JooqConnectionBuilder(new HikariConfig("/datasource.properties"));
    repository = new JooqCategoryRepository(jooq.getContext());

    DSLContext ctx = jooq.getContext();
    ctx.deleteFrom(CATEGORY).execute();
  }

  @Test
  void shouldSaveCategory() {
    Category category = CategoryMother.random();

    assertDoesNotThrow(() -> repository.save(category));
  }

  @Test
  void shouldNotThrowExceptionIfCategoryAlreadyExists() {
    Category category = CategoryMother.random();

    repository.save(category);
    assertDoesNotThrow(() -> repository.save(category));
  }

  @Test
  void shouldReturnAnExistingCategory() {
    Category originalCategory = CategoryMother.random();

    repository.save(originalCategory);

    Optional<Category> optCategory = repository.getById(originalCategory.getId());
    assertTrue(optCategory.isPresent());

    Category category = optCategory.get();

    assertEquals(originalCategory.getId(), category.getId());
    assertEquals(originalCategory.getName(), category.getName());
  }

  @Test
  void shouldNotReturnANonExistingCategory() {
    Category category = CategoryMother.random();
    Optional<Category> optCategory = repository.getById(category.getId());
    assertTrue(optCategory.isEmpty());
  }

  @AfterAll
  static void clean() {
    jooq.close();
  }
}