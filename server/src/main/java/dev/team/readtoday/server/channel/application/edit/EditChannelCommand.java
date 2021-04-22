package dev.team.readtoday.server.channel.application.edit;

import dev.team.readtoday.server.shared.domain.bus.command.Command;
import java.util.Collection;

public class EditChannelCommand implements Command {

  // Id to know which channel edit
  private final String id;

  // Editable values
  private final String title;
  private final String rssUrl;
  private final String description;
  private final String imageUrl;
  private final Collection<String> categories;

  public EditChannelCommand(String id, String title, String rssUrl, String description,
      String imageUrl, Collection<String> categories) {
    this.id = id;
    this.title = title;
    this.rssUrl = rssUrl;
    this.description = description;
    this.imageUrl = imageUrl;
    this.categories = categories;
  }

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getRssUrl() {
    return rssUrl;
  }

  public String getDescription() {
    return description;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public Collection<String> getCategories() {
    return categories;
  }
}
