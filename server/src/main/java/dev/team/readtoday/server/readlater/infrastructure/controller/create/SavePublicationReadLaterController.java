package dev.team.readtoday.server.readlater.infrastructure.controller.create;

import dev.team.readtoday.server.publication.application.create.CreatePublication;
import dev.team.readtoday.server.publication.application.get.GetPublication;
import dev.team.readtoday.server.publication.domain.Publication;
import dev.team.readtoday.server.publication.domain.PublicationDate;
import dev.team.readtoday.server.publication.domain.PublicationDescription;
import dev.team.readtoday.server.publication.domain.PublicationLink;
import dev.team.readtoday.server.publication.domain.PublicationTitle;
import dev.team.readtoday.server.readlater.application.AddPublication;
import dev.team.readtoday.server.shared.domain.CategoryId;
import dev.team.readtoday.server.shared.domain.PublicationId;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseController;
import dev.team.readtoday.server.shared.infrastructure.controller.authfilter.RequiresAuth;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@RequiresAuth
@Path("readlater")
public final class SavePublicationReadLaterController extends BaseController {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(SavePublicationReadLaterController.class);

  private final GetPublication getPublication;
  private final CreatePublication createPublication;
  private final AddPublication addPublication;

  @Autowired
  public SavePublicationReadLaterController(GetPublication getPublication,
      CreatePublication createPublication,
      AddPublication addPublication) {
    this.getPublication = getPublication;
    this.createPublication = createPublication;
    this.addPublication = addPublication;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response savePublication(PublicationRequest pur) {
    LOGGER.trace("Received save publication request.");

    PublicationId pubId = new PublicationId(pur.getId());
    Optional<Publication> pub = getPublication.get(pubId);

    if (pub.isEmpty()) {
      PublicationTitle pubTit = new PublicationTitle(pur.getTitle());
      PublicationDescription pubDes = new PublicationDescription(pur.getDescription());
      PublicationDate pubDat = new PublicationDate(pur.getDate());
      PublicationLink pubLin = new PublicationLink((pur.getLink()));
      Collection<CategoryId> cat = pur.getCategories().stream().map(CategoryId::fromString).collect(
          Collectors.toSet());
      createPublication.create(pubId, pubTit, pubDes, pubDat, pubLin, cat);
    }

    addPublication.add(getRequestUserId(), pubId);
    LOGGER.trace("Successful created read later publication");
    return Response.ok(pub).build();
  }
}

