package dev.team.readtoday.client.usecase.customlist.create.messages;

public class CustomListCreationRequest {

  private final String title;

  public CustomListCreationRequest(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }
}
