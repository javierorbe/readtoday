package dev.team.readtoday.server.publication.infrastructure.rss;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import dev.team.readtoday.server.publication.domain.Publication;
import dev.team.readtoday.server.publication.domain.PublicationDate;
import dev.team.readtoday.server.publication.domain.PublicationDescription;
import dev.team.readtoday.server.publication.domain.PublicationLink;
import dev.team.readtoday.server.publication.domain.PublicationTitle;
import dev.team.readtoday.server.shared.domain.CategoryId;
import dev.team.readtoday.server.shared.domain.PublicationId;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

enum TestSyndEntryList {
  ;

  static List<SyndEntryFake> buildEntryList() {
    ImmutableList.Builder<SyndEntryFake> builder = new Builder<>();

    builder.add(new SyndEntryFake(
        "https://techcrunch.com/?p=2138596",
        "Greylock GP Mike Duboe to discuss how to scale your company at TechCrunch Early Stage in July",
        "While the money flowing into Silicon Valley is reaching historic heights",
        toDate(LocalDateTime.of(2021, 4, 15, 15, 49, 54)),
        "http://feedproxy.google.com/~r/Techcrunch/~3/cjCF-LHVGiQ/",
        toSyndCategoryList("Events", "Startups")
    ));

    builder.add(new SyndEntryFake(
        "https://techcrunch.com/?p=2139185",
        "Casa Blanca raises $2.6M to build the ‘Bumble for real estate’",
        "Casa Blanca, which aims to develop a “Bumble-like app” for finding a home",
        toDate(LocalDateTime.of(2021, 4, 15, 15, 32, 23)),
        "http://feedproxy.google.com/~r/Techcrunch/~3/TgqvYS0lvtk/",
        toSyndCategoryList("Fundings & Exists", "Real Estate", "Startups")
    ));

    return builder.build();
  }

  static List<Publication> buildPublicationList() {
    ImmutableList.Builder<Publication> builder = new Builder<>();

    builder.add(new Publication(
        new PublicationId("https://techcrunch.com/?p=2138596"),
        new PublicationTitle("Greylock GP Mike Duboe to discuss how to scale your company at TechCrunch Early Stage in July"),
        new PublicationDescription("While the money flowing into Silicon Valley is reaching historic heights"),
        new PublicationDate(LocalDateTime.of(2021, 4, 15, 15, 49, 54)),
        new PublicationLink("http://feedproxy.google.com/~r/Techcrunch/~3/cjCF-LHVGiQ/"),
        toDomainCategoryCollection(2)
    ));

    builder.add(new Publication(
        new PublicationId("https://techcrunch.com/?p=2139185"),
        new PublicationTitle("Casa Blanca raises $2.6M to build the ‘Bumble for real estate’"),
        new PublicationDescription("Casa Blanca, which aims to develop a “Bumble-like app” for finding a home"),
        new PublicationDate(LocalDateTime.of(2021, 4, 15, 15, 32, 23)),
        new PublicationLink("http://feedproxy.google.com/~r/Techcrunch/~3/TgqvYS0lvtk/"),
        toDomainCategoryCollection(3)
    ));

    return builder.build();
  }

  private static Date toDate(LocalDateTime dateTime) {
    return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
  }

  private static List<CategoryFake> toSyndCategoryList(String... categoryNames) {
    return Arrays.stream(categoryNames).map(CategoryFake::new).toList();
  }

  private static Collection<CategoryId> toDomainCategoryCollection(int count) {
    return Stream.generate(CategoryId::random).limit(count).toList();
  }
}
