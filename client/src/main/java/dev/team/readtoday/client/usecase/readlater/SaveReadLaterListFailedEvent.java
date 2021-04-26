package dev.team.readtoday.client.usecase.readlater;

public class SaveReadLaterListFailedEvent {
  private final String reason;

  public SaveReadLaterListFailedEvent(String reason) {
    this.reason = reason;
  }
  public String getReason() {
    return reason;
  }
}

