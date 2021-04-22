package dev.team.readtoday.server.channel.infrastructure.controller.create;

import dev.team.readtoday.server.channel.application.create.CreateChannel;
import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelDescription;
import dev.team.readtoday.server.channel.domain.ChannelTitle;
import dev.team.readtoday.server.channel.domain.ImageUrl;
import dev.team.readtoday.server.channel.domain.RssUrl;
import dev.team.readtoday.server.shared.domain.CategoryId;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseController;
import dev.team.readtoday.server.shared.infrastructure.controller.authfilter.RequiresAuth;
import dev.team.readtoday.server.user.application.SearchUserById;
import dev.team.readtoday.server.user.domain.User;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Collection;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@RequiresAuth
@Path("channels")
public final class ChannelCreationController extends BaseController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ChannelCreationController.class);

  private final CreateChannel createChannel;
  private final SearchUserById searchUserById;

  @Context
  private UriInfo uriInfo;

  @Autowired
  public ChannelCreationController(CreateChannel createChannel, SearchUserById searchUserById) {
    this.createChannel = createChannel;
    this.searchUserById = searchUserById;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createChannel(ChannelCreationRequest request) {
    LOGGER.trace("Received channel creation request: {}", request);

    User user = searchUserById.search(getRequestUserId());

    if (!user.isAdmin()) {
      return response(Status.FORBIDDEN);
    }

    Channel channel = createChannelFromRequest(request);
    URI location = buildResourceLocation(channel);
    LOGGER.debug("Successful channel creation request: {}", channel);
    return Response.created(location).build();
  }

  private URI buildResourceLocation(Channel channel) {
    UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
    uriBuilder.path(channel.getId().toString());
    return uriBuilder.build();
  }

  private Channel createChannelFromRequest(ChannelCreationRequest req) {
    Collection<CategoryId> categories =
        req.getCategoryIds().stream()
            .map(CategoryId::fromString)
            .collect(Collectors.toSet());

    String title = req.getTitle();
    String rssUrl = req.getRssUrl();
    String description = req.getDescription();
    String imageUrl = req.getImageUrl();

    return createChannel.create(
        new ChannelTitle(title),
        RssUrl.create(rssUrl),
        new ChannelDescription(description),
        ImageUrl.create(imageUrl),
        categories
    );
  }
}
