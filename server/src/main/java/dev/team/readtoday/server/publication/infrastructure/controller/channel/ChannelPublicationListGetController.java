package dev.team.readtoday.server.publication.infrastructure.controller.channel;

import dev.team.readtoday.server.category.application.SearchCategoriesById;
import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.channel.domain.ChannelNotFound;
import dev.team.readtoday.server.publication.application.SearchPublications;
import dev.team.readtoday.server.publication.domain.Publication;
import dev.team.readtoday.server.publication.domain.RssFeedException;
import dev.team.readtoday.server.shared.domain.CategoryId;
import dev.team.readtoday.server.shared.domain.ChannelId;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseController;
import dev.team.readtoday.server.shared.infrastructure.controller.RequiresAuth;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@RequiresAuth
@Path("publications")
public final class ChannelPublicationListGetController extends BaseController {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(ChannelPublicationListGetController.class);

  private final SearchPublications searchPublications;
  private final SearchCategoriesById searchCategories;

  @Autowired
  public ChannelPublicationListGetController(SearchPublications searchPublications,
                                             SearchCategoriesById searchCategories) {
    this.searchPublications = searchPublications;
    this.searchCategories = searchCategories;
  }

  @GET
  public Response getChannelPublicationList(@QueryParam("channelId") ChannelId channelId) {
    try {
      List<Publication> publications = searchPublications.search(channelId);
      List<PublicationResponse> responseEntity =
          PublicationResponse.listOf(publications, getCategories(publications));
      return Response.ok(responseEntity).build();
    } catch (ChannelNotFound e) {
      LOGGER.debug("Channel publication list failed (channel not found).", e);
      return response(Status.NOT_FOUND, "Channel not found.");
    } catch (RssFeedException e) {
      LOGGER.debug("RSS error on channel publication list search.", e);
      return response(Status.INTERNAL_SERVER_ERROR);
    }
  }

  private Collection<Category> getCategories(Collection<Publication> publications) {
    Set<CategoryId> ids = publications.stream()
        .flatMap(pub -> pub.getCategories().stream())
        .collect(Collectors.toUnmodifiableSet());
    return searchCategories.search(ids);
  }
}
