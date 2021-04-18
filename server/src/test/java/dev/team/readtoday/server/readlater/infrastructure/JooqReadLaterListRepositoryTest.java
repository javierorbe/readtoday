package dev.team.readtoday.server.readlater.infrastructure;

import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelMother;
import dev.team.readtoday.server.channel.infrastructure.persistence.JooqChannelRepository;
import dev.team.readtoday.server.publication.domain.Publication;
import dev.team.readtoday.server.publication.domain.PublicationMother;
import dev.team.readtoday.server.publication.domain.PublicationRepository;
import dev.team.readtoday.server.publication.infrastructure.persistance.JooqPublicationRepository;
import dev.team.readtoday.server.readlater.domain.ReadLaterListRespository;
import dev.team.readtoday.server.readlater.infrastructure.persistence.JooqReadLaterRepository;
import dev.team.readtoday.server.shared.infrastructure.persistence.BaseJooqIntegrationTest;
import dev.team.readtoday.server.subscription.domain.Subscription;
import dev.team.readtoday.server.subscription.infrastructure.persistence.JooqSubscriptionRepository;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.user.domain.UserMother;
import dev.team.readtoday.server.user.domain.UserRepository;
import dev.team.readtoday.server.user.infrastructure.persistence.JooqUserRepository;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.*;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.Random.class)
@Tag("IntegrationTest")
public class JooqReadLaterListRepositoryTest extends BaseJooqIntegrationTest {

    private static ReadLaterListRespository readLaterListRepository;
    private static UserRepository repositoryUser;
    private static PublicationRepository publicationRepository;

    @BeforeAll
    static void setup() {
        start(READLATER, USER, PUBLICATION);
        readLaterListRepository = getRepository(JooqReadLaterRepository.class);
        repositoryUser = getRepository(JooqUserRepository.class);
        publicationRepository = getRepository(JooqPublicationRepository.class);
    }

    @AfterAll
    static void clean() {
        clearAndShutdown();
    }

    @Test
    void shouldAddPublication() {

        User user = UserMother.random();
        Publication publication = PublicationMother.random();

        }
    }



