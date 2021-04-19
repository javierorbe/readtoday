package dev.team.readtoday.server.shared.domain.bus.command;

@FunctionalInterface
public interface CommandHandler<T extends Command> {

  void handle(T command);
}
