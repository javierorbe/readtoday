package dev.team.readtoday.server.shared.domain.bus.command;

@FunctionalInterface
public interface CommandBus {

  void dispatch(Command command);
}
