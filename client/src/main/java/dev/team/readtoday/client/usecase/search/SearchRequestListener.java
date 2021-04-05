package dev.team.readtoday.client.usecase.search;

import com.google.common.collect.ImmutableCollection;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import dev.team.readtoday.client.model.Channel;
import dev.team.readtoday.client.storage.UserJwtTokenStorage;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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
      Response response = categoryNameTarget.request(MediaType.APPLICATION_JSON)
          .header(HttpHeaders.AUTHORIZATION, "Bearer " + UserJwtTokenStorage.getToken()).get();

      if (isResponseStatusOk(response)) {
        ChannelsByCategoryResponse entity = response.readEntity(ChannelsByCategoryResponse.class);
        ImmutableCollection<Channel> channels = entity.toChannelCollection();
        eventBus.post(new SearchResultReceivedEvent(channels));
      } else {
        String reason = getResponseReason(response);
        eventBus.post(new ChannelSearchRequestFailedEvent(reason));
      }
    });
  }

  private static boolean isResponseStatusOk(Response response) {
    return response.getStatus() == Response.Status.OK.getStatusCode();
  }

  private static String getResponseReason(Response response) {
    return Response.Status.fromStatusCode(response.getStatus()).getReasonPhrase();
  }
}
