package dev.team.readtoday.server.subscription.application;

import dev.team.readtoday.server.shared.domain.ChannelId;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.subscription.domain.Subscription;
import dev.team.readtoday.server.subscription.domain.SubscriptionRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@TestMethodOrder(MethodOrderer.Random.class)
final class CreateSubscriptionTest {

  @Test
  void shouldSaveSubscription() {
    SubscriptionRepository repository = mock(SubscriptionRepository.class);
    CreateSubscription createSubscription = new CreateSubscription(repository);
    UserId userId = UserId.random();
    ChannelId channelId = ChannelId.random();
    createSubscription.create(userId, channelId);

    var subscriptionCaptor = ArgumentCaptor.forClass(Subscription.class);
    verify(repository).save(subscriptionCaptor.capture());
    assertEquals(userId, subscriptionCaptor.getValue().getUserId());
    assertEquals(channelId, subscriptionCaptor.getValue().getChannelId());
  }

}



