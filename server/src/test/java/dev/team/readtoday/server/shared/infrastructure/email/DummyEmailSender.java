package dev.team.readtoday.server.shared.infrastructure.email;

import dev.team.readtoday.server.user.application.sendemail.EmailContent;
import dev.team.readtoday.server.user.application.sendemail.EmailSender;
import dev.team.readtoday.server.user.application.sendemail.EmailSubject;
import dev.team.readtoday.server.user.domain.EmailAddress;

public class DummyEmailSender implements EmailSender {

  @Override
  public void send(EmailAddress to,
                   EmailSubject subject,
                   EmailContent content) {
    throw new UnsupportedOperationException();
  }
}
