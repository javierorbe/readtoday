package dev.team.readtoday.client.usercase.subscription;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import dev.team.readtoday.client.storage.UserJwtTokenStorage;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class SubscriptionListener {

  Logger LOGGER = LoggerFactory.getLogger(SubscriptionListener.class);

  private final EventBus eventBus;
  private final WebTarget subscriptionTarget;

  private final ExecutorService executorService = Executors.newSingleThreadExecutor();

  public SubscriptionListener(EventBus eventBus, WebTarget subscriptionTarget) {
    this.eventBus = eventBus;
    this.subscriptionTarget = subscriptionTarget.path("/subscription");
  }

  @Subscribe
  public void onChannelCreationRequestReceived(SubscriptionEvent event) {

    LOGGER.trace("Sending subscription request.");
    executorService.submit(() -> {
      Response response = subscriptionTarget.request(MediaType.APPLICATION_JSON)
          .header(HttpHeaders.AUTHORIZATION, "Bearer " + UserJwtTokenStorage.getToken())
          .post(Entity.entity(event.getRequest(), MediaType.APPLICATION_JSON));
      eventBus.post(new SubscriptionResponseEvent(response));
    });
  }
}
