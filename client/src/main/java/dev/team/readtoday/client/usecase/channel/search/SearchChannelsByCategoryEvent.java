package dev.team.readtoday.client.usecase.channel.search;

public final class SearchChannelsByCategoryEvent {

  private final String categoryName;

  public SearchChannelsByCategoryEvent(String categoryName) {
    this.categoryName = categoryName;
  }

  String getCategoryName() {
    return categoryName;
  }
}
