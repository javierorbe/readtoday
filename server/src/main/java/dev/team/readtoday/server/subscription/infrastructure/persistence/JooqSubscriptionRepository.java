package dev.team.readtoday.server.subscription.infrastructure.persistence;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.SUBSCRIPTION;

import dev.team.readtoday.server.channel.domain.ChannelId;
import dev.team.readtoday.server.subscription.domain.Subscription;
import dev.team.readtoday.server.subscription.domain.SubscriptionRepository;
import dev.team.readtoday.server.user.domain.UserId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.Result;

public class JooqSubscriptionRepository implements SubscriptionRepository {

  private final DSLContext dsl;

  public JooqSubscriptionRepository(DSLContext dsl) {
    this.dsl = dsl;
  }

  @Override
  public void save(Subscription subscription) {
    dsl.insertInto(SUBSCRIPTION, SUBSCRIPTION.USER_ID, SUBSCRIPTION.CHANNEL_ID)
        .values(subscription.getIdUser().toString(), subscription.getIdChannel().toString())
        .onDuplicateKeyIgnore().execute();
  }

  @Override
  public Optional<List<Subscription>> getAllByUserId(UserId userId) {
    Result<Record2<String, String>> result =
        dsl.select(SUBSCRIPTION.USER_ID, SUBSCRIPTION.CHANNEL_ID).from(SUBSCRIPTION)
            .where(SUBSCRIPTION.USER_ID.eq(userId.toString())).fetch();

    List<Subscription> subscriptions = new ArrayList<>();

    for (Record2<String, String> results : result) {
      ChannelId idChannel = ChannelId.fromString(results.getValue(SUBSCRIPTION.CHANNEL_ID));
      subscriptions.add(new Subscription(userId, idChannel));
    }
    return Optional.of(subscriptions);
  }

  @Override
  public void remove(Subscription subscription) {
    dsl.deleteFrom(SUBSCRIPTION).where(SUBSCRIPTION.USER_ID.eq(subscription.getIdUser().toString()))
        .and(SUBSCRIPTION.CHANNEL_ID.eq(subscription.getIdChannel().toString())).execute();
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
