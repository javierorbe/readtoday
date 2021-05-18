package dev.team.readtoday.server.user.application.sendemail;

import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.shared.domain.bus.command.CommandHandler;
import dev.team.readtoday.server.user.application.SearchUserById;
import dev.team.readtoday.server.user.domain.EmailAddress;
import dev.team.readtoday.server.user.domain.User;

@Service
public final class SendEmailCommandHandler implements CommandHandler<SendEmailCommand> {

  private final SendEmail sendEmail;
  private final SearchUserById searchUser;

  public SendEmailCommandHandler(SendEmail sendEmail,
                                 SearchUserById searchUser) {
    this.sendEmail = sendEmail;
    this.searchUser = searchUser;
  }

  @Override
  public void handle(SendEmailCommand cmd) {
    EmailAddress email = getEmailAddress(cmd.getUserId());
    EmailSubject subject = EmailSubject.fromString(cmd.getSubject());
    EmailContent content = EmailContent.fromString(cmd.getContent());
    sendEmail.send(email, subject, content);
  }

  private EmailAddress getEmailAddress(String userIdStr) {
    UserId userId = UserId.fromString(userIdStr);
    User user = searchUser.search(userId);
    return user.getEmailAddress();
  }
}
