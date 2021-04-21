package dev.team.readtoday.server.category.infrastructure.controller.exception;

import dev.team.readtoday.server.category.domain.CategoryNotFound;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class CategoryNotFoundMapper implements ExceptionMapper<CategoryNotFound> {

  private static final Logger LOGGER = LoggerFactory.getLogger(CategoryNotFound.class);

  @Override
  public Response toResponse(CategoryNotFound e) {
    LOGGER.debug("Category does not exists", e);
    return Response.status(Status.NOT_FOUND).build();
  }
}
