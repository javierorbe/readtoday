package dev.team.readtoday.server.user.application.sendemail;

import dev.team.readtoday.server.shared.domain.StringValueObject;

public final class EmailSubject extends StringValueObject {

  private EmailSubject(String value) {
    super(value);
  }

  public static EmailSubject fromString(String value) {
    return new EmailSubject(value);
  }
}
