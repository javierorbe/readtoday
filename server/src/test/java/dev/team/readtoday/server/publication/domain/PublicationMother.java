package dev.team.readtoday.server.publication.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import com.github.javafaker.Faker;
import dev.team.readtoday.server.shared.domain.CategoryId;
import dev.team.readtoday.server.shared.domain.PublicationId;

public enum PublicationMother {
  ;

  private static final Faker FAKER = Faker.instance();

  public static Publication random() {
    Collection<CategoryId> categories = new ArrayList<CategoryId>();
    categories.add(CategoryId.random());
    return new Publication(new PublicationId(FAKER.bothify("??##?")),
        PublicationTitleMother.random(), PublicationDescriptionMother.random(),
        new PublicationDate(LocalDateTime.now()), new PublicationLink(FAKER.bothify("???#")),
        categories);
  }
}
