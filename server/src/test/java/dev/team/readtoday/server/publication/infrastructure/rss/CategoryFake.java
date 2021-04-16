package dev.team.readtoday.server.publication.infrastructure.rss;

import com.rometools.rome.feed.CopyFrom;
import com.rometools.rome.feed.synd.SyndCategory;

final class CategoryFake implements SyndCategory {

  private final String categoryName;

  CategoryFake(String categoryName) {
    this.categoryName = categoryName;
  }

  @Override
  public String getName() {
    return categoryName;
  }

  @Override
  public void setName(String name) {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public String getTaxonomyUri() {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public void setTaxonomyUri(String taxonomyUri) {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public CategoryFake clone() {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public Class<? extends CopyFrom> getInterface() {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public void copyFrom(CopyFrom obj) {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  public String getCategoryName() {
    return categoryName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    CategoryFake other = (CategoryFake) o;
    return categoryName.equals(other.categoryName);
  }

  @Override
  public int hashCode() {
    return categoryName.hashCode();
  }
}
