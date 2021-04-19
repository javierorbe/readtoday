package dev.team.readtoday.server.channel.infrastructure.controller.exception;

import dev.team.readtoday.server.channel.domain.ChannelNotFound;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public final class ChannelNotFoundMapper implements ExceptionMapper<ChannelNotFound> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ChannelNotFoundMapper.class);

  @Override
  public Response toResponse(ChannelNotFound e) {
    LOGGER.debug("Channel not found.", e);
    return Response.status(Status.NOT_FOUND).build();
  }
}
