package dev.team.readtoday.server.shared.domain.bus.command;

public interface CommandBus {

  void dispatch(Command command);
}
