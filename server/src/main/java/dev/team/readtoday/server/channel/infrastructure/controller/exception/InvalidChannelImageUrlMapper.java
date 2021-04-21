package dev.team.readtoday.server.channel.infrastructure.controller.exception;

import dev.team.readtoday.server.channel.domain.InvalidImageUrl;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public final class InvalidChannelImageUrlMapper implements ExceptionMapper<InvalidImageUrl> {

  private static final Logger LOGGER = LoggerFactory.getLogger(InvalidChannelImageUrlMapper.class);

  @Override
  public Response toResponse(InvalidImageUrl e) {
    LOGGER.debug("Invalid channel image url.", e);
    return Response.status(Status.BAD_REQUEST).build();
  }
}
