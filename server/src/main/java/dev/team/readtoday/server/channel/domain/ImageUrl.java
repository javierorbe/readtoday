package dev.team.readtoday.server.channel.domain;

import dev.team.readtoday.server.shared.domain.StringValueObject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.validator.routines.UrlValidator;
import org.glassfish.jersey.client.JerseyInvocation.Builder;

public final class ImageUrl extends StringValueObject {

  private static final Client client = ClientBuilder.newClient();

  public ImageUrl(String value) {
    super(value);

    if (!UrlValidator.getInstance().isValid(value)) {
      throw new InvalidImageUrl("Invalid URL.");
    }
  }

  /**
   * Create an image url.
   * <p>
   * Checks for the URL content validity.
   *
   * @param value the url
   * @return the {@code ImageUrl}
   */
  public static ImageUrl create(String value) {
    try {
      URL url = new URL(value);
      if (!isValidImageUrl(url)) {
        throw new InvalidImageUrl("Error getting the image from:  " + value);
      }
    } catch (MalformedURLException e) {
      throw new InvalidImageUrl("Invalid URL.", e);
    }

    return new ImageUrl(value);
  }

  private static boolean isValidImageUrl(URL value) {
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
