package dev.team.readtoday.server.category.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.domain.CategoryName;
import dev.team.readtoday.server.category.domain.CategoryNameMother;
import dev.team.readtoday.server.category.domain.CategoryRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;

@TestMethodOrder(MethodOrderer.Random.class)
final class CreateCategoryTest {

  @Test
  void shouldSaveCategory() {
    CategoryRepository repository = mock(CategoryRepository.class);
    CreateCategory createCategory = new CreateCategory(repository);
    CategoryName categoryName = CategoryNameMother.random();
    createCategory.create(categoryName);

    var categoryCaptor = ArgumentCaptor.forClass(Category.class);
    verify(repository).save(categoryCaptor.capture());
    assertEquals(categoryName, categoryCaptor.getValue().getName());
  }
}
