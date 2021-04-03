package dev.team.readtoday.server.category.infrastructure;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CATEGORY;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CHANNEL_CATEGORIES;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.zaxxer.hikari.HikariConfig;
import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.domain.CategoryMother;
import dev.team.readtoday.server.category.domain.CategoryRepository;
import dev.team.readtoday.server.category.infrastructure.persistence.JooqCategoryRepository;
import dev.team.readtoday.server.shared.infrastructure.persistence.JooqConnectionBuilder;
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

    ctx.deleteFrom(CHANNEL_CATEGORIES).execute();
    ctx.deleteFrom(CATEGORY).execute();
  }

  @AfterAll
  static void clean() {
    jooq.close();
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
}
