package dev.team.readtoday.server.user.domain;

import dev.team.readtoday.server.shared.domain.StringValueObject;
import java.util.regex.Pattern;

public final class Username extends StringValueObject {

  private static final Pattern USERNAME_PATTERN =
      Pattern.compile("^(?=[a-zA-Z0-9._]{5,30}$)(?!.*[_.]{2})[^_.].*[^_.]$");

  public Username(String value) {
    super(value, USERNAME_PATTERN);
  }
}
