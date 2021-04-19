package dev.team.readtoday.server.subscription.infrastructure;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CHANNEL;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CHANNEL_CATEGORIES;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.SUBSCRIPTION;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.USER;
import static org.junit.jupiter.api.Assertions.*;

import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelMother;
import dev.team.readtoday.server.channel.domain.ChannelRepository;
import dev.team.readtoday.server.channel.infrastructure.persistence.JooqChannelRepository;
import dev.team.readtoday.server.shared.infrastructure.persistence.BaseJooqIntegrationTest;
import dev.team.readtoday.server.subscription.domain.Subscription;
import dev.team.readtoday.server.subscription.domain.SubscriptionMother;
import dev.team.readtoday.server.subscription.domain.SubscriptionRepository;
import dev.team.readtoday.server.subscription.infrastructure.persistence.JooqSubscriptionRepository;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.user.domain.UserMother;
import dev.team.readtoday.server.user.domain.UserRepository;
import dev.team.readtoday.server.user.infrastructure.persistence.JooqUserRepository;
import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
@Tag("IntegrationTest")
final class JooqSubscriptionRepositoryTest extends BaseJooqIntegrationTest {

    private static SubscriptionRepository subRepository;
    private static UserRepository repositoryUser;
    private static ChannelRepository repositoryChannel;

    @BeforeAll
    static void setup() {
        start(SUBSCRIPTION, CHANNEL_CATEGORIES, CHANNEL, USER);
        subRepository = getRepository(JooqSubscriptionRepository.class);
        repositoryUser = getRepository(JooqUserRepository.class);
        repositoryChannel = getRepository(JooqChannelRepository.class);
    }

    @AfterAll
    static void clean() {
        clearAndShutdown();
    }

    @Test
    void shouldSaveSubscription() {
        Subscription subscription = SubscriptionMother.random();

        assertDoesNotThrow(() -> subRepository.save(subscription));
    }

    @Test
    void shouldReturnAnExistingSubscription() {

        User user = UserMother.random();
        Channel channel = ChannelMother.random();
        Subscription originalSubscription = new Subscription(user.getId(), channel.getId());

        repositoryChannel.save(channel);
        repositoryUser.save(user);

        Optional<Subscription> optSubscription =
                subRepository.getFromId(originalSubscription.getUserId(), originalSubscription.getChannelId());
        if(optSubscription.isEmpty()) {
            subRepository.save(originalSubscription);
            optSubscription =
                    subRepository.getFromId(originalSubscription.getUserId(), originalSubscription.getChannelId());
            assertTrue(optSubscription.isPresent());
            Subscription subscription = optSubscription.get();

            assertEquals(originalSubscription.getUserId(), subscription.getUserId());
            assertEquals(originalSubscription.getChannelId(), subscription.getChannelId());
        }
    }
    @Test
    void shouldDeleteSubscription() {
        User user = UserMother.random();
        Channel channel = ChannelMother.random();
        Subscription originalSubscription = new Subscription(user.getId(), channel.getId());

        repositoryChannel.save(channel);
        repositoryUser.save(user);
        Optional<Subscription> optSubscription =
                subRepository.getFromId(originalSubscription.getUserId(), originalSubscription.getChannelId());
        subRepository.save(originalSubscription);

        if(optSubscription.isPresent()){
            subRepository.remove(originalSubscription);
            optSubscription =
                    subRepository.getFromId(originalSubscription.getUserId(), originalSubscription.getChannelId());
            assertFalse(optSubscription.isPresent());

        }
    }
}
