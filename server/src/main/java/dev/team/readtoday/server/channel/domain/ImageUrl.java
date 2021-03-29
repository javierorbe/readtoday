package dev.team.readtoday.server.channel.domain;

import dev.team.readtoday.server.shared.domain.UrlValueObject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URL;
import org.glassfish.jersey.client.JerseyInvocation.Builder;

public final class ImageUrl extends UrlValueObject {

  private static final Client client = ClientBuilder.newClient();

  public ImageUrl(URL value) {
    super(value);
    if (!isValid(value)) {
      throw new InvalidImageUrl("Error getting the image from:  " + value);
    }
  }

  private static boolean isValid(URL value) {
    WebTarget imageTarget = client.target(value.toString());
    Builder builder = (Builder) imageTarget.request();
    Response response = builder.get();
    MediaType type = response.getMediaType();

    // Include image/svg+xml?
    return switch (type.toString()) {
      case "image/jpeg", "image/png" -> true;
      default -> false;
    };
  }
}
