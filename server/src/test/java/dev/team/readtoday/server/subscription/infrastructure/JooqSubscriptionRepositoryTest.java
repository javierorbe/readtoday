package dev.team.readtoday.server.subscription.infrastructure;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CHANNEL;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CHANNEL_CATEGORIES;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.SUBSCRIPTION;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.USER;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.zaxxer.hikari.HikariConfig;
import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelMother;
import dev.team.readtoday.server.channel.domain.ChannelRepository;
import dev.team.readtoday.server.channel.infrastructure.persistence.JooqChannelRepository;
import dev.team.readtoday.server.shared.infrastructure.persistence.JooqConnectionBuilder;
import dev.team.readtoday.server.subscription.domain.Subscription;
import dev.team.readtoday.server.subscription.domain.SubscriptionMother;
import dev.team.readtoday.server.subscription.domain.SubscriptionRepository;
import dev.team.readtoday.server.subscription.infrastructure.persistence.JooqSubscriptionRepository;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.user.domain.UserMother;
import dev.team.readtoday.server.user.domain.UserRepository;
import dev.team.readtoday.server.user.infrastructure.persistence.JooqUserRepository;
import java.util.Optional;
import org.jooq.DSLContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
@Tag("IntegrationTest")
final class JooqSubscriptionRepositoryTest {

  private static JooqConnectionBuilder jooq;
  private static SubscriptionRepository repository;
  private static UserRepository repositoryUser;
  private static ChannelRepository repositoryChannel;

  @BeforeAll
  static void setup() {
    jooq = new JooqConnectionBuilder(new HikariConfig("/datasource.properties"));
    repository = new JooqSubscriptionRepository(jooq.getContext());
    repositoryChannel = new JooqChannelRepository(jooq.getContext());
    repositoryUser = new JooqUserRepository(jooq.getContext());
    clearRepositories();
  }

  @AfterAll
  static void clean() {
    clearRepositories();
    jooq.close();
  }

  private static void clearRepositories() {
    DSLContext ctx = jooq.getContext();
    ctx.deleteFrom(SUBSCRIPTION).execute();
    ctx.deleteFrom(CHANNEL_CATEGORIES).execute();
    ctx.deleteFrom(CHANNEL).execute();
    ctx.deleteFrom(USER).execute();
  }

  @Test
  void shouldSaveSubscription() {
    Subscription subscription = SubscriptionMother.random();

    assertDoesNotThrow(() -> repository.save(subscription));
  }

  @Test
  void shouldReturnAnExistingSubscription() {

    User user = UserMother.random();
    Channel channel = ChannelMother.random();
    Subscription originalSubscription = new Subscription(user.getId(), channel.getId());

    repositoryChannel.save(channel);
    repositoryUser.save(user);
    repository.save(originalSubscription);

    Optional<Subscription> optSubscription =
        repository.getFromId(originalSubscription.getUserId(), originalSubscription.getChannelId());
    assertTrue(optSubscription.isPresent());
    Subscription subscription = optSubscription.get();

    assertEquals(originalSubscription.getUserId(), subscription.getUserId());
    assertEquals(originalSubscription.getChannelId(), subscription.getChannelId());

  }
}
