package dev.team.readtoday.client.usecase.readlater;

public class SaveReadLaterListRequestedEvent {
  private final ReadLaterRequest request;

  public SaveReadLaterListRequestedEvent(
      ReadLaterRequest request) {
    this.request = request;
  }

  public ReadLaterRequest getRequest() {
    return request;
  }
}
