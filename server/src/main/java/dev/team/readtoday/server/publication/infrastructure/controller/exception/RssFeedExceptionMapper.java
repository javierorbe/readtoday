package dev.team.readtoday.server.publication.infrastructure.controller.exception;

import dev.team.readtoday.server.publication.domain.RssFeedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public final class RssFeedExceptionMapper implements ExceptionMapper<RssFeedException> {

  private static final Logger LOGGER = LoggerFactory.getLogger(RssFeedExceptionMapper.class);

  @Override
  public Response toResponse(RssFeedException e) {
    LOGGER.error("Error on RSS feed.", e);
    return Response.serverError().build();
  }
}
