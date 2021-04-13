package dev.team.readtoday.client.usecase.channel.search;

import com.google.common.collect.ImmutableCollection;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import dev.team.readtoday.client.model.Channel;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;

public final class SearchRequestListener {

  private final EventBus eventBus;
  private final HttpRequestBuilder requestBuilder;

  public SearchRequestListener(EventBus eventBus, HttpRequestBuilderFactory factory) {
    this.eventBus = eventBus;
    requestBuilder = factory.buildWithAuth("/channels");
    eventBus.register(this);
  }

  @Subscribe
  public void onSearchChannelsByCategory(SearchChannelsByCategoryEvent event) {
    HttpResponse response =
        requestBuilder.withParam("categoryName", event.getCategoryName()).get();

    if (response.isStatusOk()) {
      ChannelsByCategoryResponse entity = response.getEntity(ChannelsByCategoryResponse.class);
      ImmutableCollection<Channel> channels = entity.toChannelCollection();
      eventBus.post(new SearchResultReceivedEvent(channels));
    } else {
      String reason = response.getStatusReason();
      eventBus.post(new ChannelSearchRequestFailedEvent(reason));
    }
  }
}
