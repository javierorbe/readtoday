package dev.team.readtoday.client.usecase.auth.accesstoken;

import com.google.common.eventbus.EventBus;
import dev.team.readtoday.client.usecase.auth.AuthInfoProvider;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

final class JerseyConfig extends ResourceConfig {

  private static final String ACCESS_TOKEN_LISTENER_PACKAGE =
      "dev.team.readtoday.client.usecase.auth.accesstoken";

  JerseyConfig(EventBus eventBus, AuthInfoProvider authInfoProvider) {
    packages(ACCESS_TOKEN_LISTENER_PACKAGE);
    register(new AbstractBinder() {
      @Override
      protected void configure() {
        bind(eventBus).to(EventBus.class);
        bind(authInfoProvider).to(AuthInfoProvider.class);
      }
    });
  }
}
