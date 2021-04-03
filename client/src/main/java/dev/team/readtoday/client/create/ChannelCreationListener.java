package dev.team.readtoday.client.create;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import dev.team.readtoday.client.storage.UserJwtTokenStorage;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ChannelCreationListener {

  private final EventBus eventBus;
  private final WebTarget channelsTarget;

  private final ExecutorService executorService = Executors.newSingleThreadExecutor();

  public ChannelCreationListener(EventBus eventBus, WebTarget channelsTarget) {
    this.eventBus = eventBus;
    this.channelsTarget = channelsTarget.path("/channels");
  }

  @Subscribe
  public void onChannelCreationRequestReceived(ChannelCreationEvent event) {
    executorService.submit(() -> {
      Response response = channelsTarget.request(MediaType.APPLICATION_JSON)
          .header(HttpHeaders.AUTHORIZATION, "Bearer " + UserJwtTokenStorage.getToken())
          .post(Entity.entity(event.getRequest(), MediaType.APPLICATION_JSON));
      eventBus.post(new ChannelCreationResponseEvent(response));
    });
  }
}
