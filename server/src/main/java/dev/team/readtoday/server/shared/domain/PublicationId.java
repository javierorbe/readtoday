package dev.team.readtoday.server.shared.domain;

/**
 * String that uniquely identifies a publication.
 *
 * @see <a href="https://validator.w3.org/feed/docs/rss2.html#ltguidgtSubelementOfLtitemgt">RSS 2.0 specification - item guid</a>
 */
public final class PublicationId extends StringValueObject {

  public PublicationId(String value) {
    super(value);
  }
}