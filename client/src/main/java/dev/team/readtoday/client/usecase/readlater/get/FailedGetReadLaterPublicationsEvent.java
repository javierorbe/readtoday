package dev.team.readtoday.client.usecase.readlater.get;

public class FailedGetReadLaterPublicationsEvent {

  private String reason;

  public FailedGetReadLaterPublicationsEvent(String reason) {
    this.reason = reason;
  }

  public String getReason() {
    return this.reason;
  }
}
