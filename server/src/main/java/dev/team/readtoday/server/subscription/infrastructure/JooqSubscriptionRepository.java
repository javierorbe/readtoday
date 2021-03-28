package dev.team.readtoday.server.subscription.infrastructure;


import dev.team.readtoday.server.subscription.domain.Subscription;
import dev.team.readtoday.server.subscription.domain.SubscriptionRepository;

import org.jooq.DSLContext;

public class JooqSubscriptionRepository implements SubscriptionRepository {
	 
  private final DSLContext dsl;

  public JooqChannelRepository(DSLContext dsl) {
    this.dsl = dsl;
  }
  
  @Override
  public void save(Subscription subscription) {
	  dsl.insertInto(SUBSCRIPTION, SUBSCRIPTION.ID_USER, SUBSCRIPTION.ID_CHANNEL)
      .values(
          subscription.getIdUser().toString(),
          subscription.getIdChannel().toString()
      ).execute();	
  }

}
