package dev.team.readtoday.server.category.domain;

import java.io.Serial;

public final class CategoryDoesNotExist extends Exception {

  @Serial
  private static final long serialVersionUID = -6384716463017408470L;

  public CategoryDoesNotExist(CategoryName categoryName) {
    super("Category with name '" + categoryName + "' does not exist.");
  }
}
