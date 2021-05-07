package dev.team.readtoday.server.user.application.sendemail;

import dev.team.readtoday.server.shared.domain.bus.command.Command;

public final class SendEmailCommand implements Command {

  private final String userId;
  private final String subject;
  private final String content;

  /**
   * Send an email to a user.
   *
   * @param userId the ID of the user that the email is sent to
   * @param subject the subject of the email
   * @param content the content of the email
   */
  public SendEmailCommand(String userId, String subject, String content) {
    this.userId = userId;
    this.subject = subject;
    this.content = content;
  }

  public String getUserId() {
    return userId;
  }

  public String getSubject() {
    return subject;
  }

  String getContent() {
    return content;
  }
}
