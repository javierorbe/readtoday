package dev.team.readtoday.server.subscription.infrastructure.persistence;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.SUBSCRIPTION;

import dev.team.readtoday.server.shared.domain.ChannelId;
import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.subscription.domain.Subscription;
import dev.team.readtoday.server.subscription.domain.SubscriptionRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.Result;

@Service
public class JooqSubscriptionRepository implements SubscriptionRepository {

  private final DSLContext dsl;

  public JooqSubscriptionRepository(DSLContext dsl) {
    this.dsl = dsl;
  }

  @Override
  public void save(Subscription subscription) {
    dsl.insertInto(SUBSCRIPTION, SUBSCRIPTION.USER_ID, SUBSCRIPTION.CHANNEL_ID)
        .values(subscription.getUserId().toString(), subscription.getChannelId().toString())
        .onDuplicateKeyIgnore().execute();
  }

  @Override
  public Collection<Subscription> getAllByUserId(UserId userId) {
    Result<Record2<String, String>> result =
        dsl.select(SUBSCRIPTION.USER_ID, SUBSCRIPTION.CHANNEL_ID).from(SUBSCRIPTION)
            .where(SUBSCRIPTION.USER_ID.eq(userId.toString())).fetch();

    List<Subscription> subscriptions = new ArrayList<>();

    for (Record2<String, String> results : result) {
      ChannelId idChannel = ChannelId.fromString(results.getValue(SUBSCRIPTION.CHANNEL_ID));
      subscriptions.add(new Subscription(userId, idChannel));
    }
      Collection<Subscription> collection = subscriptions;
    return collection ;

  }

  @Override
  public void remove(Subscription subscription) {
    dsl.deleteFrom(SUBSCRIPTION).where(SUBSCRIPTION.USER_ID.eq(subscription.getUserId().toString()))
        .and(SUBSCRIPTION.CHANNEL_ID.eq(subscription.getChannelId().toString())).execute();
  }

  @Override
  public Optional<Subscription> getFromId(UserId idU, ChannelId idC) {
    Record2<String, String> result = dsl.select(SUBSCRIPTION.USER_ID, SUBSCRIPTION.CHANNEL_ID)
        .from(SUBSCRIPTION).where(SUBSCRIPTION.USER_ID.eq(idU.toString()))
        .and(SUBSCRIPTION.CHANNEL_ID.eq(idC.toString())).fetchOne();

    if (result == null) {
      return Optional.empty();
    }
    Subscription subscription = new Subscription(idU, idC);
    return Optional.of(subscription);
  }
}
