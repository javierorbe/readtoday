package dev.team.readtoday.client.usecase.subscription.publications;

import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import dev.team.readtoday.client.usecase.shared.response.PublicationResponse;
import java.util.Collection;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public final class PublicationRequestListener {

  private final EventBus eventBus;
  private final HttpRequestBuilder requestBuilder;

  PublicationRequestListener(EventBus eventBus, HttpRequestBuilderFactory factory) {
    this.eventBus = eventBus;
    requestBuilder = factory.build("/subscriptions/publications");
  }

  @Subscribe(threadMode = ThreadMode.ASYNC)
  public void onPublicationRequest() {
    HttpResponse response = requestBuilder.get();

    if (response.isStatusOk()) {
      PublicationListResponse entity = response.getEntity(PublicationListResponse.class);
      Collection<PublicationResponse> publications = entity.getPublications();
      eventBus.post(new PublicationRequestSuccesfulEvent(publications));
    } else {
      String reason = response.getStatusReason();
      eventBus.post(new PublicationRequestFailedEvent(reason));
    }
  }
}
