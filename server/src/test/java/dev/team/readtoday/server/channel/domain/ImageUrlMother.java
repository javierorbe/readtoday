package dev.team.readtoday.server.channel.domain;

import com.github.javafaker.Faker;
import com.google.common.collect.ImmutableList;

public enum ImageUrlMother {
  ;

  private static final ImmutableList<ImageUrl> URLS = ImmutableList.of(
      new ImageUrl("https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png"),
      new ImageUrl("https://i.pinimg.com/originals/ca/76/0b/ca760b70976b52578da88e06973af542.jpg")
  );

  private static final Faker FAKER = Faker.instance();

  public static ImageUrl random() {
    return URLS.get(FAKER.random().nextInt(URLS.size()));
  }
}
