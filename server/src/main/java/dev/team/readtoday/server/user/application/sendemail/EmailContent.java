package dev.team.readtoday.server.user.application.sendemail;

import dev.team.readtoday.server.shared.domain.StringValueObject;

public final class EmailContent extends StringValueObject {

  private EmailContent(String value) {
    super(value);
  }

  public static EmailContent fromString(String value) {
    return new EmailContent(value);
  }
}
