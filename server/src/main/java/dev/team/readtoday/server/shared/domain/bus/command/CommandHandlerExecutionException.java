package dev.team.readtoday.server.shared.domain.bus.command;

import java.io.Serial;

public final class CommandHandlerExecutionException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -5434724309386253225L;

  public CommandHandlerExecutionException(Throwable cause) {
    super(cause);
  }
}
