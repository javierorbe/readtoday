package dev.team.readtoday.server.shared.infrastructure.bus.query;

import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.bus.query.Query;
import dev.team.readtoday.server.shared.domain.bus.query.QueryDoesNotHaveHandler;
import dev.team.readtoday.server.shared.domain.bus.query.QueryHandler;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;

@SuppressWarnings("rawtypes")
@Service
public final class QueryHandlerInfo {

  private static final String SEARCH_PACKAGE = "dev.team.readtoday.server";

  private final Map<Class<? extends Query>, Class<? extends QueryHandler>> handlers;

  public QueryHandlerInfo() {
    Reflections reflections = new Reflections(SEARCH_PACKAGE);
    Set<Class<? extends QueryHandler>> classes = reflections.getSubTypesOf(QueryHandler.class);
    handlers = buildHandlerMap(classes);
  }

  public Class<? extends QueryHandler> search(Class<? extends Query> queryClass)
      throws QueryDoesNotHaveHandler {
    Class<? extends QueryHandler> queryHandlerClass = handlers.get(queryClass);

    if (queryHandlerClass == null) {
      throw new QueryDoesNotHaveHandler();
    }

    return queryHandlerClass;
  }

  private static Map<Class<? extends Query>, Class<? extends QueryHandler>>
  buildHandlerMap(Iterable<Class<? extends QueryHandler>> classes) {
    Map<Class<? extends Query>, Class<? extends QueryHandler>> handlers = new HashMap<>();

    for (Class<? extends QueryHandler> handlerClass : classes) {
      ParameterizedType type = (ParameterizedType) handlerClass.getGenericInterfaces()[0];
      @SuppressWarnings("unchecked")
      Class<? extends Query> queryClass = (Class<? extends Query>) type.getActualTypeArguments()[0];
      handlers.put(queryClass, handlerClass);
    }

    return handlers;
  }
}
