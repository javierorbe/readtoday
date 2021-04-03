package dev.team.readtoday.client.view.home;

import dev.team.readtoday.client.model.Channel;
import java.util.List;

public interface SearchChannelController {

  /**
   * Sends a search channels by category request to server.
   *
   * @return List of new channels
   */
  List<Channel> searchNewChannelsByCategoryName(String categoryName);

}
