package dev.team.readtoday.client.usecase.settings.get;

import dev.team.readtoday.client.app.eventbus.SubscribedComponent;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

@SubscribedComponent
public final class GetSettingsListener {

  private final EventBus eventBus;
  private final HttpRequestBuilder requestBuilder;

  GetSettingsListener(EventBus eventBus, HttpRequestBuilderFactory factory) {
    this.eventBus = eventBus;
    requestBuilder = factory.buildWithAuth("/settings");
  }

  @Subscribe
  public void onGetSettings(GetSettingsEvent event) {
    HttpResponse response = requestBuilder.get();

    if (response.isStatusOk()) {
      var entity = response.getEntity(GetSettingsResponse.class);
      eventBus.post(
          new SettingsReceivedEvent(entity.getZoneId(), entity.getNotificationPref())
      );
    } else {
      String reason = response.getStatusReason();
      eventBus.post(new FailedToGetSettingsEvent(reason));
    }
  }
}
