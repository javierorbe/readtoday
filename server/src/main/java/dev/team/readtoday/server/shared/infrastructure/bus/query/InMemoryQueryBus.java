package dev.team.readtoday.server.shared.infrastructure.bus.query;

import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.bus.query.Query;
import dev.team.readtoday.server.shared.domain.bus.query.QueryBus;
import dev.team.readtoday.server.shared.domain.bus.query.QueryDoesNotHaveHandler;
import dev.team.readtoday.server.shared.domain.bus.query.QueryHandler;
import dev.team.readtoday.server.shared.domain.bus.query.QueryHandlerExecutionException;
import dev.team.readtoday.server.shared.domain.bus.query.QueryResponse;
import org.springframework.context.ApplicationContext;

@Service
public final class InMemoryQueryBus implements QueryBus {

  private final QueryHandlerInfo handlerInfo;
  private final ApplicationContext context;

  public InMemoryQueryBus(QueryHandlerInfo handlerInfo,
                          ApplicationContext context) {
    this.handlerInfo = handlerInfo;
    this.context = context;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public <R extends QueryResponse> R ask(Query<R> query) {
    try {
      Class<? extends QueryHandler> handlerClass = handlerInfo.search(query.getClass());
      QueryHandler handler = context.getBean(handlerClass);
      return (R) handler.handle(query);
    } catch (QueryDoesNotHaveHandler e) {
      throw new QueryHandlerExecutionException(e);
    }
  }
}
