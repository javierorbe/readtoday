package dev.team.readtoday.server.publication.infrastructure.rss;

import com.rometools.rome.feed.CopyFrom;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.synd.SyndCategory;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEnclosure;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndLink;
import com.rometools.rome.feed.synd.SyndPerson;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.jdom2.Element;

final class SyndEntryFake implements SyndEntry {

  private final String uri;
  private final String title;
  private final SyndContent description;
  private final String link;
  private final Date date;
  private final List<CategoryFake> categories;

  SyndEntryFake(String uri,
                String title,
                String description,
                Date date,
                String link,
                List<CategoryFake> categories) {
    this.uri = uri;
    this.title = title;
    this.description = new DescriptionFake(description);
    this.link = link;
    this.date = date;
    this.categories = categories;
  }

  @Override
  public String getUri() {
    return uri;
  }

  @Override
  public void setUri(String uri) {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public String getTitle() {
    return title;
  }

  @Override
  public void setTitle(String title) {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public SyndContent getTitleEx() {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public void setTitleEx(SyndContent title) {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public String getLink() {
    return link;
  }

  @Override
  public void setLink(String link) {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public List<SyndLink> getLinks() {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public void setLinks(List<SyndLink> links) {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public SyndContent getDescription() {
    return description;
  }

  @Override
  public void setDescription(SyndContent description) {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public List<SyndContent> getContents() {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public void setContents(List<SyndContent> contents) {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public List<SyndEnclosure> getEnclosures() {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public void setEnclosures(List<SyndEnclosure> enclosures) {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public Date getPublishedDate() {
    return new Date(date.getTime());
  }

  @Override
  public void setPublishedDate(Date publishedDate) {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public Date getUpdatedDate() {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public void setUpdatedDate(Date updatedDate) {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public List<SyndPerson> getAuthors() {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public void setAuthors(List<SyndPerson> authors) {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public String getAuthor() {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public void setAuthor(String author) {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public List<SyndPerson> getContributors() {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public void setContributors(List<SyndPerson> contributors) {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public List<SyndCategory> getCategories() {
    return Collections.unmodifiableList(categories);
  }

  @Override
  public void setCategories(List<SyndCategory> categories) {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public SyndFeed getSource() {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public void setSource(SyndFeed source) {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public Object getWireEntry() {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public Module getModule(String uri) {
    // TODO: Implement method.
    throw new UnsupportedOperationException("Not implemented, yet.");
  }

  @Override
  public List<Module> getModules() {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public void setModules(List<Module> modules) {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public List<Element> getForeignMarkup() {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public void setForeignMarkup(List<Element> foreignMarkup) {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public String getComments() {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public void setComments(String comments) {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public Object clone() {
    throw new UnsupportedOperationException("Unsupported operation.");
  }

  @Override
  public SyndLink findRelatedLink(String relation) {
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

  public List<String> getCategoryNames() {
    return categories.stream().map(CategoryFake::getCategoryName).toList();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    SyndEntryFake other = (SyndEntryFake) o;
    return uri.equals(other.uri) && title.equals(other.title) && description.equals(other.description) && link
        .equals(other.link) && date.equals(other.date) && categories.equals(other.categories);
  }

  @Override
  public int hashCode() {
    int result = uri.hashCode();
    result = (31 * result) + title.hashCode();
    result = (31 * result) + description.hashCode();
    result = (31 * result) + link.hashCode();
    result = (31 * result) + date.hashCode();
    result = (31 * result) + categories.hashCode();
    return result;
  }
}
