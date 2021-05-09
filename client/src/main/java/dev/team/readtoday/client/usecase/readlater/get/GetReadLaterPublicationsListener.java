package dev.team.readtoday.client.usecase.readlater.get;

import java.util.Collection;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.team.readtoday.client.app.eventbus.SubscribedComponent;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import dev.team.readtoday.client.usecase.shared.response.PublicationResponse;

@SubscribedComponent
public class GetReadLaterPublicationsListener {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(GetReadLaterPublicationsListener.class);

  private final EventBus eventBus;
  private final HttpRequestBuilder requestBuilder;

  GetReadLaterPublicationsListener(EventBus eventBus, HttpRequestBuilderFactory factory) {
    this.eventBus = eventBus;
    this.requestBuilder = factory.buildWithAuth("/readlater");
  }

  @Subscribe(threadMode = ThreadMode.ASYNC)
  public void onPublicationGetRequest(GetReadLaterPublicationsEvent event) {
    LOGGER.trace("Read later publication list requested.");

    HttpResponse response = requestBuilder.get();

    if (response.isStatusOk()) {
      GetReadLaterPublicationsResponse entity =
          response.getEntity(GetReadLaterPublicationsResponse.class);
      Collection<PublicationResponse> publicationList = entity.getPublications();
      eventBus.post(new SuccesfulGetReadLaterPublicationsEvent(publicationList));
    } else {
      eventBus.post(new FailedGetReadLaterPublicationsEvent(response.getStatusReason()));
    }
  }
}
