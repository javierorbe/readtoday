package dev.team.readtoday.server.subscription.application.notification;

import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.bus.command.CommandHandler;

@Service
public final class StartNotificationSenderCommandHandler
    implements CommandHandler<StartNotificationSenderCommand> {

  private final NotificationSender notificationSender;

  public StartNotificationSenderCommandHandler(NotificationSender notificationSender) {
    this.notificationSender = notificationSender;
  }

  @Override
  public void handle(StartNotificationSenderCommand command) {
    new Thread(notificationSender).start();
  }
}
