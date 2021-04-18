package dev.team.readtoday.server.shared.domain.bus.query;

public interface QueryBus {

  <R extends QueryResponse> R ask(Query<R> query);
}
