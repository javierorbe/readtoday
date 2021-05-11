package dev.team.readtoday.client.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

final class PublicationMother {

  static OffsetDateTime aa = OffsetDateTime.parse("20150101", DateTimeFormatter.ofPattern("yyyyMMdd"));

  static Publication withName(String name) {
    return new Publication(
        UUID.randomUUID().toString(), name, "aa",aa, "aa",Set.of());}
}
