package dev.team.readtoday.server.readlater.domain;

import dev.team.readtoday.server.shared.domain.PublicationId;
import dev.team.readtoday.server.shared.domain.UserId;

import java.util.Optional;


public interface ReadLaterListRespository {

    Optional<ReadLaterList> getByUserId(UserId userId);
    void addPublication(UserId userId, PublicationId publicationId);

}
