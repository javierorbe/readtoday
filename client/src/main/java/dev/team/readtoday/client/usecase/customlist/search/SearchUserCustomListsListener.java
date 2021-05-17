package dev.team.readtoday.client.usecase.customlist.search;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.team.readtoday.client.app.eventbus.SubscribedComponent;
import dev.team.readtoday.client.usecase.customlist.search.event.SearchUserCustomListsEvent;
import dev.team.readtoday.client.usecase.customlist.search.event.SearchUserCustomListsFailedEvent;
import dev.team.readtoday.client.usecase.customlist.search.event.SearchUserCustomListsSuccefulEvent;
import dev.team.readtoday.client.usecase.customlist.search.messages.SearchUserCustomListsResponse;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;

@SubscribedComponent
public class SearchUserCustomListsListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(SearchUserCustomListsListener.class);

  private final EventBus eventBus;
  private final HttpRequestBuilder requestBuilder;

  public SearchUserCustomListsListener(EventBus eventBus, HttpRequestBuilderFactory factory) {
    this.eventBus = eventBus;
    this.requestBuilder = factory.buildWithAuth("custom-list/search");
  }

  @Subscribe(threadMode = ThreadMode.ASYNC)
  public void onSearchUserCustomListsRequest(SearchUserCustomListsEvent event) {
    LOGGER.trace("Sending search user's custom lists request.");
    HttpResponse response = requestBuilder.get();
    if (response.isStatusOk()) {
      LOGGER.trace("Custom lists recived successfully");
      SearchUserCustomListsResponse entity =
          response.getEntity(SearchUserCustomListsResponse.class);
      eventBus.post(new SearchUserCustomListsSuccefulEvent(entity.getList()));
    } else {
      LOGGER.trace("Custom lists were not found.");
      eventBus.post(new SearchUserCustomListsFailedEvent(response.getStatusReason()));
    }
  }
}
