package dev.team.readtoday.server.publication.infrastructure.rss;

import com.rometools.rome.feed.CopyFrom;
import com.rometools.rome.feed.synd.SyndContent;

final class DescriptionFake implements SyndContent {

  private final String value;

  DescriptionFake(String value) {
    this.value = value;
  }

  @Override
  public String getType() {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public void setType(String type) {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public String getMode() {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public void setMode(String mode) {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public String getValue() {
    return value;
  }

  @Override
  public void setValue(String value) {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public DescriptionFake clone() {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public Class<? extends CopyFrom> getInterface() {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public void copyFrom(CopyFrom obj) {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    DescriptionFake other = (DescriptionFake) o;
    return value.equals(other.value);
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }
}
