package dev.team.readtoday.server.endpoints;

import dev.team.readtoday.server.shared.infrastructure.jooq.tables.Channel;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

/**
 * Root resource (exposed at "channel" path).
 */
@Path("channel")
public class ChannelEndpoint {


  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Channel> getChannel() {

    // TODO: Get channels of the db
    Optional<List<Channel>> channels = Optional.empty();

    return channels.orElse(List.of());
  }

}
