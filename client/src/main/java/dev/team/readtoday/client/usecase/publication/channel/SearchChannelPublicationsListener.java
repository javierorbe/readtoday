package dev.team.readtoday.client.usecase.publication.channel;

import com.google.common.collect.ImmutableList;
import dev.team.readtoday.client.app.eventbus.SubscribedComponent;
import dev.team.readtoday.client.model.Publication;
import dev.team.readtoday.client.usecase.shared.GenericType;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import dev.team.readtoday.client.usecase.shared.response.PublicationResponse;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@SubscribedComponent
public final class SearchChannelPublicationsListener {

  private static final PublicationResponseListType PUBLICATION_RESPONSE_LIST_TYPE =
      new PublicationResponseListType();

  private final EventBus eventBus;
  private final HttpRequestBuilder requestBuilder;

  public SearchChannelPublicationsListener(EventBus eventBus,
                                           HttpRequestBuilderFactory factory) {
    this.eventBus = eventBus;
    requestBuilder = factory.buildWithAuth("/publications");
  }

  @Subscribe(threadMode = ThreadMode.ASYNC)
  public void onSearchChannelPublications(SearchChannelPublicationsEvent event) {
    String channelId = event.getChannelId();
    HttpResponse response = requestBuilder.withParam("channelId", channelId).get();

    if (response.isStatusOk()) {
      List<PublicationResponse> entity = response.getEntity(PUBLICATION_RESPONSE_LIST_TYPE);
      ImmutableList<Publication> publications = PublicationResponse.deserializeList(entity);
      eventBus.post(new ChannelPublicationsFoundEvent(channelId, publications));
    } else {
      String reason = response.getStatusReason();
      eventBus.post(new SearchChannelPublicationsFailedEvent(reason));
    }
  }

  private static final class PublicationResponseListType
      extends GenericType<List<PublicationResponse>> {


  }
}
