package dev.team.readtoday.server.channel.infrastructure.controller.exception;

import dev.team.readtoday.server.channel.domain.InvalidChannelTitle;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public final class InvalidChannelTitleMapper implements ExceptionMapper<InvalidChannelTitle> {

  private static final Logger LOGGER = LoggerFactory.getLogger(InvalidChannelTitleMapper.class);

  @Override
  public Response toResponse(InvalidChannelTitle e) {
    LOGGER.debug("Invalid channel title.", e);
    return Response.status(Status.BAD_REQUEST).build();
  }
}
