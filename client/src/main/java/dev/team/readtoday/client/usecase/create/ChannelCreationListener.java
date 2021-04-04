package dev.team.readtoday.client.usecase.create;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ChannelCreationListener {

  Logger LOGGER = LoggerFactory.getLogger(ChannelCreationListener.class);

  private final EventBus eventBus;
  private final WebTarget channelsTarget;

  private final ExecutorService executorService = Executors.newSingleThreadExecutor();

  public ChannelCreationListener(EventBus eventBus, WebTarget channelsTarget) {
    this.eventBus = eventBus;
    this.channelsTarget = channelsTarget.path("/channels");
  }

  @Subscribe
  public void onChannelCreationRequestReceived(ChannelCreationEvent event) {

    LOGGER.trace("Sending channel creation request.");
    executorService.submit(() -> {
      Response response = channelsTarget.request(MediaType.APPLICATION_JSON)
          .header(HttpHeaders.AUTHORIZATION, "Bearer " + UserJwtTokenStorage.getToken())
          .post(Entity.entity(event.getRequest(), MediaType.APPLICATION_JSON));
      eventBus.post(new ChannelCreationResponseEvent(response));
    });
  }
}
