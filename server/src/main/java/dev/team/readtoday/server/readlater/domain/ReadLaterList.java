package dev.team.readtoday.server.readlater.domain;

import dev.team.readtoday.server.publication.domain.Publication;
import dev.team.readtoday.server.shared.domain.PublicationId;
import dev.team.readtoday.server.shared.domain.UserId;

import java.util.Collection;

public class ReadLaterList {

    private final UserId userId;
    private final Collection<PublicationId> publicationlist;

    public ReadLaterList(UserId userId,
                         Collection<PublicationId> publicationlist){
        this.userId = userId;
        this.publicationlist = publicationlist;
    }

    public UserId getUserId() { return userId; }

    public Collection<PublicationId> getPublicationlist() { return publicationlist; }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        ReadLaterList rll = (ReadLaterList) o;
        return userId.equals(rll.userId);
    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }
}

