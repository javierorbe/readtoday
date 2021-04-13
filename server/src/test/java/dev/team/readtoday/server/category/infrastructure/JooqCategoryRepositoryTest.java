package dev.team.readtoday.server.category.infrastructure;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.domain.CategoryMother;
import dev.team.readtoday.server.category.domain.CategoryRepository;
import dev.team.readtoday.server.category.infrastructure.persistence.JooqCategoryRepository;
import dev.team.readtoday.server.shared.domain.CategoryId;
import dev.team.readtoday.server.shared.infrastructure.persistence.BaseJooqIntegrationTest;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
@Tag("IntegrationTest")
final class JooqCategoryRepositoryTest extends BaseJooqIntegrationTest {

  private static CategoryRepository repository;

  @BeforeAll
  static void beforeAll() {
    start();
    repository = getRepository(JooqCategoryRepository.class);
  }

  @AfterAll
  static void afterAll() {
    clearAndShutdown();
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
  void shouldReturnExistingCategoriesById() {
    Collection<Category> expectedCategories =
        Stream.generate(CategoryMother::random)
            .limit(5L)
            .collect(Collectors.toSet());
    expectedCategories.forEach(category -> repository.save(category));

    Collection<CategoryId> identifiers =
        expectedCategories.stream()
            .map(Category::getId)
            .collect(Collectors.toSet());

    Collection<Category> categories = repository.getById(identifiers);
    assertEquals(expectedCategories, categories);
  }

  @Test
  void shouldReturnAnExistingCategoryByName() {
    Category expectedCategory = CategoryMother.random();
    repository.save(expectedCategory);

    Optional<Category> optCategory = repository.getByName(expectedCategory.getName());

    assertTrue(optCategory.isPresent());
    assertEquals(expectedCategory, optCategory.get());
  }

  @Test
  void shouldNotReturnANonExistentCategoryByName() {
    Category category = CategoryMother.random();

    Optional<Category> optCategory = repository.getByName(category.getName());

    assertTrue(optCategory.isEmpty());
  }
}
