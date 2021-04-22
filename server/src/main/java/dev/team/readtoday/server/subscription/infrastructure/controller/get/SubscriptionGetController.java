package dev.team.readtoday.server.subscription.infrastructure.controller.get;


import dev.team.readtoday.server.category.application.SearchAllCategories;
import dev.team.readtoday.server.category.application.search.SearchCategory;
import dev.team.readtoday.server.category.domain.Category;
import dev.team.readtoday.server.category.domain.CategoryName;
import dev.team.readtoday.server.channel.application.SearchChannelsFromSubscriptions;
import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.shared.domain.CategoryId;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseController;
import dev.team.readtoday.server.shared.infrastructure.controller.authfilter.RequiresAuth;
import dev.team.readtoday.server.subscription.application.GetUserSubscriptions;
import dev.team.readtoday.server.subscription.domain.Subscription;
import dev.team.readtoday.server.subscription.domain.SubscriptionNotFound;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiresAuth
@Path("subscriptions")
public final class SubscriptionGetController extends BaseController {

  private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionGetController.class);

  private final SearchChannelsFromSubscriptions searchChannels;
  private final SearchCategory searchCategoriesById;


  @Autowired
  public SubscriptionGetController(SearchChannelsFromSubscriptions searchChannels,
      SearchCategory searchCategoriesById) {
    this.searchChannels = searchChannels;
    this.searchCategoriesById = searchCategoriesById;

  }

  @GET
  @Consumes(MediaType.APPLICATION_JSON)
  public Response getSubscription() {

    try {
      Collection<Channel> subscriptions = searchChannels.search(getRequestUserId());
      Collection<CategoryId> categoryIds = flatChannelCategories(subscriptions);
      Collection<Category> categories = searchCategoriesById.apply(categoryIds);
      AllSubscriptionsResponse response = new AllSubscriptionsResponse(subscriptions, categories);
      return Response.ok(response).build();
    } catch (SubscriptionNotFound e) {
      LOGGER.debug("Error getting subscriptions.", e);
      return response(Response.Status.BAD_REQUEST);
    }
  }

  private static Collection<CategoryId> flatChannelCategories(Collection<Channel> channels) {
    return channels.stream()
        .flatMap(channel -> channel.getCategories().stream())
        .collect(Collectors.toSet());
  }


}
