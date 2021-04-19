package dev.team.readtoday.server.shared.domain.bus.query;

@FunctionalInterface
public interface QueryBus {

  <R extends QueryResponse> R ask(Query<R> query);
}
