package dev.team.readtoday.client.usecase.channel.search;

import com.google.common.collect.ImmutableCollection;
import dev.team.readtoday.client.app.eventbus.SubscribedComponent;
import dev.team.readtoday.client.model.Channel;
import dev.team.readtoday.client.usecase.channel.search.events.SearchChannelsByCategoryEvent;
import dev.team.readtoday.client.usecase.channel.search.events.SearchChannelsByCategoryFailedEvent;
import dev.team.readtoday.client.usecase.channel.search.events.SearchChannelsByCategorySuccessfullyEvent;
import dev.team.readtoday.client.usecase.channel.search.messages.ChannelsByCategoryResponse;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@SubscribedComponent
public final class SearchChannelsByCategoryListener {

  private final EventBus eventBus;
  private final HttpRequestBuilder requestBuilder;

  SearchChannelsByCategoryListener(EventBus eventBus, HttpRequestBuilderFactory factory) {
    this.eventBus = eventBus;
    requestBuilder = factory.buildWithAuth("/channels");
  }

  @Subscribe(threadMode = ThreadMode.ASYNC)
  public void onSearchChannelsByCategory(SearchChannelsByCategoryEvent event) {
    HttpResponse response =
        requestBuilder.withParam("categoryName", event.getCategoryName()).get();

    if (response.isStatusOk()) {
      ChannelsByCategoryResponse entity = response.getEntity(ChannelsByCategoryResponse.class);
      ImmutableCollection<Channel> channels = entity.toChannelCollection();
      eventBus.post(new SearchChannelsByCategorySuccessfullyEvent(channels));
    } else {
      String reason = response.getStatusReason();
      eventBus.post(new SearchChannelsByCategoryFailedEvent(reason));
    }
  }
}
