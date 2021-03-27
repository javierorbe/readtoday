package dev.team.readtoday.server.channel.domain;

import static net.andreinc.mockneat.types.enums.DomainSuffixType.POPULAR;
import static net.andreinc.mockneat.types.enums.HostNameType.ADVERB_VERB;
import static net.andreinc.mockneat.types.enums.URLSchemeType.HTTP;
import net.andreinc.mockneat.MockNeat;

public enum UrlMother {
  ;

  private static final MockNeat m = MockNeat.threadLocal();

  public static Url random() {

    String url = m.urls()
      .scheme(HTTP) // all the URLS have a HTTP scheme
                    //.auth() -- can include auth information in the URL
      .domain(POPULAR) // only popular domain names can be used
      .host(ADVERB_VERB)
      .get();

    return new Url(url);
  }

}
