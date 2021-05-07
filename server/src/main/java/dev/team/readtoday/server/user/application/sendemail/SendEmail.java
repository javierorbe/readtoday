package dev.team.readtoday.server.user.application.sendemail;

import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.user.domain.EmailAddress;

@Service
public final class SendEmail {

  private final EmailSender emailSender;

  public SendEmail(EmailSender emailSender) {
    this.emailSender = emailSender;
  }

  public void send(EmailAddress to, EmailSubject subject, EmailContent content) {
    emailSender.send(to, subject, content);
  }
}
