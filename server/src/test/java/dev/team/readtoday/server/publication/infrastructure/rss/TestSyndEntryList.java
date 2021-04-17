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
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

enum TestSyndEntryList {
  ;

  static List<Publication> buildPublicationList() {
    ImmutableList.Builder<Publication> builder = new Builder<>();

    builder.add(new Publication(
        new PublicationId("https://techcrunch.com/?p=2138596"),
        new PublicationTitle("Greylock GP Mike Duboe to discuss how to scale your company at TechCrunch Early Stage in July"),
        new PublicationDescription("While the money flowing into Silicon Valley is reaching historic heights"),
        new PublicationDate(OffsetDateTime.of(2021, 4, 15, 15, 49, 54, 0, ZoneOffset.UTC)),
        new PublicationLink("http://feedproxy.google.com/~r/Techcrunch/~3/cjCF-LHVGiQ/"),
        toDomainCategoryCollection(2L)
    ));

    builder.add(new Publication(
        new PublicationId("https://techcrunch.com/?p=2139185"),
        new PublicationTitle("Casa Blanca raises $2.6M to build the ‘Bumble for real estate’"),
        new PublicationDescription("Casa Blanca, which aims to develop a “Bumble-like app” for finding a home"),
        new PublicationDate(OffsetDateTime.of(2021, 4, 15, 15, 32, 23, 0, ZoneOffset.UTC)),
        new PublicationLink("http://feedproxy.google.com/~r/Techcrunch/~3/TgqvYS0lvtk/"),
        toDomainCategoryCollection(3L)
    ));

    return builder.build();
  }

  private static Collection<CategoryId> toDomainCategoryCollection(long count) {
    return Stream.generate(CategoryId::random).limit(count).toList();
  }
}
