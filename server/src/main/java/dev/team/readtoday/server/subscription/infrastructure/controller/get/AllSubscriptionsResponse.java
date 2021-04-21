package dev.team.readtoday.server.subscription.infrastructure.controller.get;

import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.infrastructure.controller.search.CategoryResponse;
import dev.team.readtoday.server.channel.infrastructure.controller.search.ChannelResponse;
import java.util.Collection;
import java.util.List;

public class AllSubscriptionsResponse {

  private final List<ChannelResponse> subscriptionList;
  private final List<CategoryResponse> categoryList;

  public AllSubscriptionsResponse(Collection<Channel> subscriptionList,
      Collection<Category> categoryList) {
    this.subscriptionList = ChannelResponse.fromChannels(subscriptionList);
    this.categoryList = CategoryResponse.fromCategories(categoryList);
  }

  public List<ChannelResponse> getSubscriptionList() {
    return subscriptionList;
  }

  public List<CategoryResponse> getCategoryList() {
    return categoryList;
  }
}
