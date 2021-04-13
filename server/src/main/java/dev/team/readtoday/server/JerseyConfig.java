package dev.team.readtoday.server;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.ConfigurableApplicationContext;

final class JerseyConfig extends ResourceConfig {

  private static final String SERVER_PACKAGE = "dev.team.readtoday.server";

  JerseyConfig(ConfigurableApplicationContext context) {
    packages(SERVER_PACKAGE);
    property("contextConfig", context);
  }
}
