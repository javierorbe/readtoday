package dev.team.readtoday.server.customlist.application;

import dev.team.readtoday.server.shared.domain.bus.command.Command;

public class CreateCustomListCommand implements Command {

  private final String title;
  private final String userId;

  public CreateCustomListCommand(String title, String userId) {
    this.title = title;
    this.userId = userId;
  }

  public String getTitle() {
    return title;
  }

  public String getUserId() {
    return userId;
  }
}
