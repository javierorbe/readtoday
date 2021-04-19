package dev.team.readtoday.server.readlater.application;

import dev.team.readtoday.server.readlater.domain.ReadLaterListRespository;
import dev.team.readtoday.server.shared.domain.PublicationId;
import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.UserId;

@Service
public class AddPublication {

    private final ReadLaterListRespository readLaterListRespository;

    public AddPublication(ReadLaterListRespository readLaterListRespository){
        this.readLaterListRespository = readLaterListRespository;
    }
    public void add(UserId userId, PublicationId publicationId){
        readLaterListRespository.addPublication(userId,publicationId);
    }

}
