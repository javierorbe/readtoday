package dev.team.readtoday.client.usecase.settings.update;

import dev.team.readtoday.client.app.eventbus.SubscribedComponent;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

@SubscribedComponent
public final class UpdateSettingsListener {

  private final EventBus eventBus;
  private final HttpRequestBuilder requestBuilder;

  UpdateSettingsListener(EventBus eventBus, HttpRequestBuilderFactory factory) {
    this.eventBus = eventBus;
    requestBuilder = factory.buildWithAuth("/settings");
  }

  @Subscribe
  public void onUpdateSettingsRequest(UpdateSettingsEvent event) {
    var request = new UpdateSettingsRequest(
        event.getZoneId().getId(),
        event.getNotificationPreference().toString()
    );

    HttpResponse response = requestBuilder.put(request);

    if (response.isStatusOk()) {
      eventBus.post(
          new SettingsSuccessfullyUpdated(event.getZoneId(), event.getNotificationPreference())
      );
    } else {
      String reason = response.getStatusReason();
      eventBus.post(new FailedSettingsUpdate(reason));
    }
  }
}
