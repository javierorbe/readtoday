package dev.team.readtoday.server.shared.infrastructure.controller;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.ConfigurableApplicationContext;

final class TestingJerseyConfig extends ResourceConfig {

  private static final String SERVER_PACKAGE = "dev.team.readtoday.server";

  TestingJerseyConfig(ConfigurableApplicationContext context) {
    packages(SERVER_PACKAGE);
    property("contextConfig", context);
  }
}
