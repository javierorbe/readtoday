package dev.team.readtoday.server.channel.domain;

import dev.team.readtoday.server.shared.domain.StringValueObject;

public final class ChannelTitle extends StringValueObject {

  private static final int MAX_LENGTH = 30;

  public ChannelTitle(String value) {
    super(value);
    if (!isValidTitleChannel(value)) {
      throw new InvalidChannelTitle("Invalid channel title: " + value);
    }
  }

  private static boolean isValidTitleChannel(String value) {
    return value.length() < MAX_LENGTH;
  }
}
