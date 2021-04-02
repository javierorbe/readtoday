package dev.team.readtoday.client.jersey.search;

import dev.team.readtoday.client.model.Channel;
import dev.team.readtoday.client.view.home.SearchChannelController;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.client.ResponseProcessingException;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JerseySearchChannelController implements SearchChannelController {

  private static final Logger LOGGER = LoggerFactory.getLogger(JerseySearchChannelController.class);

  private final WebTarget channelTarget;

  public JerseySearchChannelController(WebTarget baseTarget) {
    this.channelTarget = baseTarget.path("/channels");
  }

  @Override
  public List<Channel> searchNewChannelsByCategoryName(String categoryName) {
    GenericType<ChannelsByCategoryResponse> genericType = new GenericType<>() {};

    WebTarget categoryNameTarget = channelTarget.queryParam("categoryName", categoryName);

    try {
      ChannelsByCategoryResponse response =
          categoryNameTarget.request(MediaType.APPLICATION_JSON).get(genericType);
      LOGGER.debug("The request was successful");

      return response.toChannels();
    } catch (ResponseProcessingException e1) {
      LOGGER.debug("The request failed during conversion to response");
    } catch (ProcessingException e2) {
      LOGGER.debug("The request failed processing ");
    } catch (WebApplicationException e3) {
      LOGGER.debug("The request failed because of the response ?");
    }

    return List.of();
  }
}
