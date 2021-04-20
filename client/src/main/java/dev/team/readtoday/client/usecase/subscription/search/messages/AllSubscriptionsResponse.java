package dev.team.readtoday.client.usecase.subscription.search.messages;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import dev.team.readtoday.client.model.Category;
import dev.team.readtoday.client.model.Channel;
import dev.team.readtoday.client.usecase.channel.search.messages.CategoryResponse;
import dev.team.readtoday.client.usecase.channel.search.messages.ChannelResponse;

import java.util.List;
import java.util.Map;

public class AllSubscriptionsResponse {
    private List<ChannelResponse> subscriptionList;
    private List<CategoryResponse> categoryList;

    public List<ChannelResponse> getSubscriptionList() {
        return subscriptionList;
    }

    public List<CategoryResponse> getCategoryList() {
        return categoryList;
    }

    public void setSubscriptionList(List<ChannelResponse> subscriptionList) {
        this.subscriptionList = subscriptionList;
    }

    public void setCategoryList(List<CategoryResponse> categoryList) {
        this.categoryList = categoryList;
    }

    public ImmutableCollection<Channel> toChannelsCollection() {
        Map<String, Category> categoryMap = CategoryResponse.buildCategoryMap(categoryList);
        return subscriptionList.stream()
                .map(channel -> channel.toModel(categoryMap))
                .collect(ImmutableSet.toImmutableSet());
    }
}
