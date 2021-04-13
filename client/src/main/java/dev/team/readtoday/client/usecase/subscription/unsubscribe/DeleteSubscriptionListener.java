package dev.team.readtoday.client.usecase.subscription.unsubscribe;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import dev.team.readtoday.client.usecase.shared.AuthTokenSupplier;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class DeleteSubscriptionListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(DeleteSubscriptionListener.class);

  private final EventBus eventBus;
  private final WebTarget unsubscriptionTarget;
  private AuthTokenSupplier token;

  private final ExecutorService executorService = Executors.newSingleThreadExecutor();

  public DeleteSubscriptionListener(EventBus eventBus, WebTarget unsubscriptionTarget,
      AuthTokenSupplier token) {
    this.eventBus = eventBus;
    this.unsubscriptionTarget = unsubscriptionTarget.path("/subscription");
    this.token = token;
  }

  @Subscribe
  public void onChannelCreationRequestReceived(DeleteSubscriptionEvent event) {


    WebTarget channelIdTarget = unsubscriptionTarget.queryParam("channelId", event.getChannelId());
    LOGGER.trace("Sending unsubscription request.");

    executorService.submit(() -> {

      Response response = channelIdTarget.request(MediaType.APPLICATION_JSON)
          .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.getAuthToken()).delete();

      if (isResponseStatusOk(response)) {

        eventBus.post(new DeleteSubscriptionSuccessfulEvent());
      } else {
        eventBus.post(new DeleteSubscriptionFailedEvent(event.getChannelId(),
            response.getStatusInfo().getReasonPhrase()));
      }

    });
  }

  private static boolean isResponseStatusOk(Response response) {
    return response.getStatus() == Response.Status.NO_CONTENT.getStatusCode();
  }
}
