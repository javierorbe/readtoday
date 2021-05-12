package dev.team.readtoday.server.customlist.infrastructure.controller.add;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import dev.team.readtoday.server.readlater.application.AddPublication;
import dev.team.readtoday.server.shared.domain.PublicationId;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseController;
import dev.team.readtoday.server.shared.infrastructure.controller.authfilter.RequiresAuth;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequiresAuth
@Path("custom-list/add")
public class CustomListAddController extends BaseController {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomListAddController.class);

  private final AddPublication addPublication;

  @Autowired
  public CustomListAddController(AddPublication addPublication) {
    this.addPublication = addPublication;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addPublicationToCustomList(CustomListAddRequest request) {
    LOGGER.trace("Recived publication add request to custom list.");

    addPublication.add(getRequestUserId(), PublicationId.fromString(request.getPublicationId()));

    return Response.ok().build();
  }
}
