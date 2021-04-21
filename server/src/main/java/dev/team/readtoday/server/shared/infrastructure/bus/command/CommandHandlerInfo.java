package dev.team.readtoday.server.shared.infrastructure.bus.command;

import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.bus.command.Command;
import dev.team.readtoday.server.shared.domain.bus.command.CommandDoesNotHaveHandler;
import dev.team.readtoday.server.shared.domain.bus.command.CommandHandler;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;

@SuppressWarnings("rawtypes")
@Service
public final class CommandHandlerInfo {

  private static final String SEARCH_PACKAGE = "dev.team.readtoday.server";

  private final Map<Class<? extends Command>, Class<? extends CommandHandler>> handlers;

  public CommandHandlerInfo() {
    Reflections reflections = new Reflections(SEARCH_PACKAGE);
    Set<Class<? extends CommandHandler>> classes = reflections.getSubTypesOf(CommandHandler.class);
    handlers = buildHandlerMap(classes);
  }

  public Class<? extends CommandHandler> search(Class<? extends Command> commandClass)
      throws CommandDoesNotHaveHandler {
    Class<? extends CommandHandler> handlerClass = handlers.get(commandClass);

    if (handlerClass == null) {
      throw new CommandDoesNotHaveHandler();
    }

    return handlerClass;
  }

  @SuppressWarnings("unchecked")
  private static Map<Class<? extends Command>, Class<? extends CommandHandler>> buildHandlerMap(
      Iterable<Class<? extends CommandHandler>> cmdHandlerClasses) {
    Map<Class<? extends Command>, Class<? extends CommandHandler>> handlers = new HashMap<>();

    for (Class<? extends CommandHandler> handlerClass : cmdHandlerClasses) {
      ParameterizedType type = (ParameterizedType) handlerClass.getGenericInterfaces()[0];
      Class<? extends Command> commandClass =
          (Class<? extends Command>) type.getActualTypeArguments()[0];
      handlers.put(commandClass, handlerClass);
    }

    return handlers;
  }
}
