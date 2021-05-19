package dev.team.readtoday.server.settings.application.update;

import dev.team.readtoday.server.settings.domain.NotificationPreference;
import dev.team.readtoday.server.settings.domain.TimeZone;
import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.shared.domain.bus.command.CommandHandler;

@Service
public final class UpdateSettingsCommandHandler implements CommandHandler<UpdateSettingsCommand> {

  private final UpdateSettings updateSettings;

  public UpdateSettingsCommandHandler(UpdateSettings updateSettings) {
    this.updateSettings = updateSettings;
  }

  @Override
  public void handle(UpdateSettingsCommand cmd) {
    updateSettings.update(
        UserId.fromString(cmd.getUserId()),
        NotificationPreference.fromString(cmd.getNotificationPreference()),
        TimeZone.fromString(cmd.getZoneId())
    );
  }
}
