package dev.team.readtoday.client.search;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import dev.team.readtoday.client.model.Channel;
import dev.team.readtoday.client.storage.UserJwtTokenStorage;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class SearchRequestListener {

  private final EventBus eventBus;
  private final WebTarget channelsTarget;

  private final ExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

  public SearchRequestListener(EventBus eventBus, WebTarget serverBaseTarget) {
    this.eventBus = eventBus;
    this.channelsTarget = serverBaseTarget.path("/channels");
  }

  @Subscribe
  public void onSearchChannelsByCategory(SearchChannelsByCategoryEvent event) {
    WebTarget categoryNameTarget =
        channelsTarget.queryParam("categoryName", event.getCategoryName());

    executorService.submit(() -> {
      ChannelsByCategoryResponse response = categoryNameTarget.request(MediaType.APPLICATION_JSON)
          .header(HttpHeaders.AUTHORIZATION, "Bearer " + UserJwtTokenStorage.getToken())
          .get(ChannelsByCategoryResponse.class);
      List<Channel> channels = response.toChannels();
      eventBus.post(new SearchResultReceivedEvent(channels));
    });
  }
}
