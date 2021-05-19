package dev.team.readtoday.server.channel.infrastructure.controller.search;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.domain.CategoryMother;
import dev.team.readtoday.server.category.domain.CategoryName;
import dev.team.readtoday.server.shared.domain.CategoryId;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
public class CategoryResponseTest {

  @Test
  void shouldGetCorrectly() {
    Category category1 = CategoryMother.random();
    Category category2 = CategoryMother.random();

    Collection<Category> categories = Arrays.asList(category1, category2);

    Collection<CategoryResponse> categoryResponses = CategoryResponse.fromCategories(categories);

    assertEquals(categories.size(), categoryResponses.size());

    Collection<Category> categoriesMapped = categoryResponses.stream().map(categoryResponse -> {
      String id = categoryResponse.getId();
      String name = categoryResponse.getName();
      return new Category(CategoryId.fromString(id), new CategoryName(name));
    }).collect(Collectors.toList());

    assertEquals(categories, categoriesMapped);
  }
}
