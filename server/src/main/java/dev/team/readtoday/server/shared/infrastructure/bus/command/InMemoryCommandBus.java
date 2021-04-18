package dev.team.readtoday.server.shared.infrastructure.bus.command;

import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.bus.command.Command;
import dev.team.readtoday.server.shared.domain.bus.command.CommandBus;
import dev.team.readtoday.server.shared.domain.bus.command.CommandDoesNotHaveHandler;
import dev.team.readtoday.server.shared.domain.bus.command.CommandHandler;
import dev.team.readtoday.server.shared.domain.bus.command.CommandHandlerExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

@Service
public final class InMemoryCommandBus implements CommandBus {

  private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryCommandBus.class);

  private final CommandHandlerInfo handlerInfo;
  private final ApplicationContext context;

  public InMemoryCommandBus(CommandHandlerInfo handlerInfo,
                            ApplicationContext context) {
    this.handlerInfo = handlerInfo;
    this.context = context;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public void dispatch(Command command) {
    try {
      Class<? extends CommandHandler> handlerClass = handlerInfo.search(command.getClass());
      CommandHandler handler = context.getBean(handlerClass);
      handler.handle(command);
    } catch (CommandDoesNotHaveHandler e) {
      throw new CommandHandlerExecutionException(e);
    }
  }
}
