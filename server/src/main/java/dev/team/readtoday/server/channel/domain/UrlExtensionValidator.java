package dev.team.readtoday.server.channel.domain;

public class UrlExtensionValidator {

  public static boolean isImage(String value) {
    return value.endsWith(".jpg") &&
    value.endsWith(".png");
  }

  public static boolean isRss(String value) {
    return value.endsWith(".xml");
  }

}
