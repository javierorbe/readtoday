package dev.team.readtoday.server.shared.domain.bus.query;

@FunctionalInterface
public interface QueryHandler<Q extends Query<R>, R extends QueryResponse> {

  R handle(Q query);
}
