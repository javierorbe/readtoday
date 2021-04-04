package dev.team.readtoday.server.subscription.infrastructure;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CHANNEL;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CHANNEL_CATEGORIES;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.net.MalformedURLException;
import java.util.Optional;
import org.jooq.DSLContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import com.zaxxer.hikari.HikariConfig;
import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelMother;
import dev.team.readtoday.server.channel.domain.ChannelRepository;
import dev.team.readtoday.server.channel.infrastructure.persistence.JooqChannelRepository;
import dev.team.readtoday.server.shared.infrastructure.persistence.JooqConnectionBuilder;
import dev.team.readtoday.server.subscription.domain.Subscription;
import dev.team.readtoday.server.subscription.domain.SubscriptionMother;
import dev.team.readtoday.server.subscription.domain.SubscriptionRepository;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.user.domain.UserMother;
import dev.team.readtoday.server.user.domain.UserRepository;
import dev.team.readtoday.server.user.infrastructure.persistence.JooqUserRepository;

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
   
    DSLContext ctx = jooq.getContext();
    ctx.deleteFrom(CHANNEL_CATEGORIES).execute();
    ctx.deleteFrom(CHANNEL).execute();
    
    repositoryChannel = new JooqChannelRepository(jooq.getContext());
    
    repositoryUser = new JooqUserRepository(jooq.getContext());
  }

  @Test
  void shouldSaveSubscription() {
    Subscription subscription = SubscriptionMother.random();

    assertDoesNotThrow(() -> repository.save(subscription));

  }

  @Test
  void shouldReturnAnExistingSubscription() throws MalformedURLException {

    User user = UserMother.random();
    Channel channel = ChannelMother.random();
    Subscription originalSubscription = new Subscription(user.getId(), channel.getId());

    repositoryChannel.save(channel);
    repositoryUser.save(user);
    repository.save(originalSubscription);

    Optional<Subscription> optSubscription =
        repository.getFromId(originalSubscription.getIdUser(), originalSubscription.getIdChannel());
    assertTrue(optSubscription.isPresent());
    Subscription subscription = optSubscription.get();

    assertEquals(originalSubscription.getIdUser(), subscription.getIdUser());
    assertEquals(originalSubscription.getIdChannel(), subscription.getIdChannel());

  }

  @AfterAll
  static void clean() {
    jooq.close();
  }
}
