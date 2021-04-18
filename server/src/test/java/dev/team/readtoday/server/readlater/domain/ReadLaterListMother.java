package dev.team.readtoday.server.readlater.domain;

import com.github.javafaker.Faker;
import dev.team.readtoday.server.shared.domain.PublicationId;
import dev.team.readtoday.server.shared.domain.UserId;

import java.util.ArrayList;
import java.util.List;

public enum ReadLaterListMother {
    ;

    private static final Faker FAKER = Faker.instance();
    public static ReadLaterList random() {
        List<PublicationId> ids = new ArrayList<>();

        for(int j = 0; j<10;j++) {
            ids.add(new PublicationId(FAKER.bothify("??##?")));
        }
        return new ReadLaterList(UserId.random(),ids);
    }

}
