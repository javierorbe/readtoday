package dev.team.readtoday.server.user.application.sendemail;

import dev.team.readtoday.server.user.domain.EmailAddress;

public interface EmailSender {

  void send(EmailAddress to, EmailSubject subject, EmailContent content);
}
