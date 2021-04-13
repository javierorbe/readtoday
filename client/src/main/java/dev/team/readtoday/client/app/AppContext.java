package dev.team.readtoday.client.app;

import org.greenrobot.eventbus.EventBus;
import dev.team.readtoday.client.usecase.shared.AuthTokenSupplier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.tomlj.TomlTable;

final class AppContext extends AnnotationConfigApplicationContext {

  private static final String APP_PACKAGE = "dev.team.readtoday.client";

  AppContext(EventBus eventBus, TomlTable config, AuthTokenSupplier authTokenSupplier) {
    registerBean(TomlTable.class, () -> config);
    registerBean(EventBus.class, () -> eventBus);
    registerBean(AuthTokenSupplier.class, () -> authTokenSupplier);
    scan(APP_PACKAGE);
    refresh();
  }
}
