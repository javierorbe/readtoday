package dev.team.readtoday.server.channel.domain;

import java.net.MalformedURLException;
import java.net.URL;

public enum ImageUrlMother {
  ;

  private static final String png =
      "https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png";
  private static final String jpg =
      "https://i.pinimg.com/originals/ca/76/0b/ca760b70976b52578da88e06973af542.jpg";

  private static final String invalidImage = "https://www.google.es/";


  static ImageUrl getPNG() throws MalformedURLException {
    return new ImageUrl(new URL(png));
  }

  static ImageUrl getJPG() throws MalformedURLException {
    return new ImageUrl(new URL(jpg));
  }

  static ImageUrl getInvalidImageURL() throws MalformedURLException {
    return new ImageUrl(new URL(invalidImage));
  }
}
