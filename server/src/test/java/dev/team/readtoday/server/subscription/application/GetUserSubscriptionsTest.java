package dev.team.readtoday.server.subscription.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.team.readtoday.server.shared.domain.ChannelId;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.subscription.domain.Subscription;
import dev.team.readtoday.server.subscription.domain.SubscriptionRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
public class GetUserSubscriptionsTest {

  @Test
  void shouldReturnUserSubscriptions() {
    SubscriptionRepository repository = mock(SubscriptionRepository.class);
    Collection<ChannelId> channelIds = Stream.generate(ChannelId::random).limit(10L).toList();
    UserId userId = UserId.random();
    List<Subscription> expectedSubscriptions = new ArrayList<>();
    for (ChannelId channelId : channelIds) {
      expectedSubscriptions.add(new Subscription(userId, channelId));
    }
    when(repository.getAllByUserId(userId)).thenReturn(expectedSubscriptions);
    GetUserSubscriptions getUserSubscriptions = new GetUserSubscriptions(repository);
    Collection<Subscription> actualSubscriptions = getUserSubscriptions.search(userId);

    assertEquals(expectedSubscriptions, actualSubscriptions);
  }

}


