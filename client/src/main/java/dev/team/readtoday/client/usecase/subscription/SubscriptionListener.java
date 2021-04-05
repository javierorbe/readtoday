package dev.team.readtoday.client.usecase.subscription;

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

public final class SubscriptionListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionListener.class);

  private final EventBus eventBus;
  private final WebTarget subscriptionTarget;

  private final ExecutorService executorService = Executors.newSingleThreadExecutor();

  public SubscriptionListener(EventBus eventBus, WebTarget subscriptionTarget) {
    this.eventBus = eventBus;
    this.subscriptionTarget = subscriptionTarget.path("/subscription");
  }

  @Subscribe
  public void onSubscriptionRequested(SubscriptionRequestedEvent event) {
    String channelId = event.getChannelId();
    LOGGER.trace("Subscription requested for channel: {}", channelId);
    executorService.submit(() -> {
      SubscriptionRequest request = new SubscriptionRequest(channelId);
      Response response = subscriptionTarget.request(MediaType.APPLICATION_JSON)
          .header(HttpHeaders.AUTHORIZATION, "Bearer " + UserJwtTokenStorage.getToken())
          .post(Entity.entity(request, MediaType.APPLICATION_JSON));

      if (isResponseStatusCreated(response)) {
        eventBus.post(new SuccessfulSubscriptionEvent(channelId));
      } else {
        eventBus.post(new SubscriptionFailedEvent(channelId, getResponseReason(response)));
      }
    });
  }

  private static boolean isResponseStatusCreated(Response response) {
    return response.getStatus() == Response.Status.CREATED.getStatusCode();
  }

  private static String getResponseReason(Response response) {
    return Response.Status.fromStatusCode(response.getStatus()).getReasonPhrase();
  }
}
