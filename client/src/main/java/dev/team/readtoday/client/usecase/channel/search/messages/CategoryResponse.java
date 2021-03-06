package dev.team.readtoday.client.usecase.channel.search.messages;

import dev.team.readtoday.client.model.Category;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public final class CategoryResponse {

  private String id;
  private String name;

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public static Map<String, Category> buildCategoryMap(Collection<CategoryResponse> categories) {
    return categories.stream()
        .collect(Collectors.toMap(cat -> cat.id, cat -> new Category(cat.id, cat.name)));
  }
}
