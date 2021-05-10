package dev.team.readtoday.server.user.infrastructure.email;

import dev.team.readtoday.server.user.application.sendemail.EmailContent;
import dev.team.readtoday.server.user.application.sendemail.EmailSender;
import dev.team.readtoday.server.user.application.sendemail.EmailSubject;
import dev.team.readtoday.server.user.domain.EmailAddress;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

public final class SimpleMailSender implements EmailSender {

  private static final String SENDER_NAME = "ReadToday";
  private static final String SENDER_ADDRESS = "noreply@readtoday.dev";

  private final Mailer mailer;

  public SimpleMailSender(String host, int port, String username, String password) {
    mailer = MailerBuilder
        .withSMTPServer(host, port, username, password)
        .async()
        .buildMailer();
  }

  @Override
  public void send(EmailAddress to,
                   EmailSubject subject,
                   EmailContent content) {
    Email email = EmailBuilder.startingBlank()
        .from(SENDER_NAME, SENDER_ADDRESS)
        .to(to.toString())
        .withSubject(subject.toString())
        .withHTMLText(content.toString())
        .buildEmail();

    mailer.sendMail(email);
  }
}
