package dev.team.readtoday.server.publication.domain;

import java.util.Optional;
import dev.team.readtoday.server.shared.domain.ChannelId;
import dev.team.readtoday.server.shared.domain.PublicationId;

public interface PublicationRepository {

  /**
   * Saves a publication
   * 
   * @param publicaiton to save
   */
  void save(Publication publicaiton);

  /**
   * Removes a publication
   * 
   * @param publicaiton to delete
   */
  void remove(Publication publication);

  /**
   * Gets a publication from an id
   * 
   * @param publicaitionId id of the publication
   * @return if exists returns a publication
   */
  Optional<Publication> getFromId(PublicationId publicaitionId);

  /**
   * Gets a relevant publication from a channel
   * 
   * @param channelId id of the channel
   * @return if there is any returns a publication
   */
  Optional<Publication> getRelevant(ChannelId channelId);
}
