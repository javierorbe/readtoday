package dev.team.readtoday.server.subscription.infrastructure;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import com.zaxxer.hikari.HikariConfig;
import dev.team.readtoday.server.channel.domain.ChannelId;
import dev.team.readtoday.server.shared.infrastructure.persistence.JooqConnectionBuilder;
import dev.team.readtoday.server.subscription.domain.Subscription;
import dev.team.readtoday.server.subscription.domain.SubscriptionMother;
import dev.team.readtoday.server.subscription.domain.SubscriptionRepository;
import dev.team.readtoday.server.user.domain.UserId;

@TestMethodOrder(MethodOrderer.Random.class)
@Tag("IntegrationTest")
final class JooqSubscriptionRepositoryTest {

  private static JooqConnectionBuilder jooq;
  private static SubscriptionRepository repository;

  @BeforeAll
  static void setup() {
    jooq = new JooqConnectionBuilder(new HikariConfig("/datasource.properties"));
    repository = new JooqSubscriptionRepository(jooq.getContext());
  }

  @Test
  void shouldSaveSubscription() {
    Subscription subscription = SubscriptionMother.random();

    assertDoesNotThrow(() -> repository.save(subscription));

  }

  @Test
  void shouldReturnAnExistingSubscription() {
    Subscription originalSubscription = SubscriptionMother.random();

    repository.save(originalSubscription);

    Optional<Subscription> optSubscription =
        repository.getFromId(originalSubscription.getIdUser(), originalSubscription.getIdChannel());
    assertTrue(optSubscription.isPresent());
    Subscription subscription = optSubscription.get();

    assertEquals(originalSubscription.getIdUser(), subscription.getIdUser());
    assertEquals(originalSubscription.getIdChannel(), subscription.getIdChannel());

  }

  @Test
  void shouldNotReturnANonExistingChannel() {
    Optional<Subscription> optSubscription =
        repository.getFromId(UserId.random(), ChannelId.random());
    assertTrue(optSubscription.isEmpty());
  }

  @AfterAll
  static void clean() {
    jooq.close();
  }
}
