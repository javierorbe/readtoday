package dev.team.readtoday.server.readlater.application;

import dev.team.readtoday.server.readlater.domain.ReadLaterList;
import dev.team.readtoday.server.readlater.domain.ReadLaterListRespository;
import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.user.domain.NonExistingUser;

@Service
public class SearchReadLaterList {

    private final ReadLaterListRespository readLaterListRespository;

    public SearchReadLaterList(ReadLaterListRespository readLaterListRespository){
        this.readLaterListRespository = readLaterListRespository;
    }
    public ReadLaterList search(UserId userId) throws NonExistingUser {
        if(readLaterListRespository.getByUserId(userId).isPresent()) {
            return readLaterListRespository.getByUserId(userId).get();
        }
        throw new NonExistingUser("User not found");
    }
}

