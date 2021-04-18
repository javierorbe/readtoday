package dev.team.readtoday.server.shared.domain.bus.query;

import java.io.Serial;

public final class QueryHandlerExecutionException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -6844089860441890738L;

  public QueryHandlerExecutionException(Throwable cause) {
    super(cause);
  }
}
