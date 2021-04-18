package dev.team.readtoday.server.readlater.domain;

import dev.team.readtoday.server.shared.domain.PublicationId;
import dev.team.readtoday.server.shared.domain.UserId;


public interface ReadLaterListRespository {

    ReadLaterList getByUserId(UserId userId);
    void addPublication(UserId userId, PublicationId publicationId);

}
