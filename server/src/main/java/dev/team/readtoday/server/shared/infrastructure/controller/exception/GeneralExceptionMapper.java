package dev.team.readtoday.server.shared.infrastructure.controller.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public final class GeneralExceptionMapper implements ExceptionMapper<Exception> {

  private static final Logger LOGGER = LoggerFactory.getLogger(GeneralExceptionMapper.class);

  @Override
  public Response toResponse(Exception e) {
    LOGGER.error("Uncaught exception on controller.", e);
    return Response.serverError().build();
  }
}
