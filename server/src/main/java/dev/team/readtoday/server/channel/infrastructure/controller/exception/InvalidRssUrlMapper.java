package dev.team.readtoday.server.channel.infrastructure.controller.exception;

import dev.team.readtoday.server.channel.domain.InvalidRssUrl;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public final class InvalidRssUrlMapper implements ExceptionMapper<InvalidRssUrl> {

  private static final Logger LOGGER = LoggerFactory.getLogger(InvalidRssUrlMapper.class);

  @Override
  public Response toResponse(InvalidRssUrl e) {
    LOGGER.debug("Invalid rss url.", e);
    return Response.status(Status.BAD_REQUEST).build();
  }

}
