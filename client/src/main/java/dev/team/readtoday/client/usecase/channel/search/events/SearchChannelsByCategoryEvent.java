package dev.team.readtoday.client.usecase.channel.search.events;

public class SearchChannelsByCategoryEvent {

  private final String categoryName;

  public SearchChannelsByCategoryEvent(String categoryName) {
    this.categoryName = categoryName;
  }

  public String getCategoryName() {
    return categoryName;
  }
}
