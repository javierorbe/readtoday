package dev.team.readtoday.server.readlater.domain;

import dev.team.readtoday.server.shared.domain.PublicationId;
import dev.team.readtoday.server.shared.domain.UserId;

import java.util.Optional;


public interface ReadLaterListRespository {

  /**
   * Returns the readlaterlist of the user given his id
   * @param userId
   * @return
   */
    Optional<ReadLaterList> getByUserId(UserId userId);

  /**
   * Add a publication to readlater list
   * @param userId
   * @param publicationId
   */
    void addPublication(UserId userId, PublicationId publicationId);

}
