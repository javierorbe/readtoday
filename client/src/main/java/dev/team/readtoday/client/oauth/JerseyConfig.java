package dev.team.readtoday.client.oauth;

import dev.team.readtoday.client.view.auth.AuthController;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

final class JerseyConfig extends ResourceConfig {

  private static final String AUTH_SERVER_PACKAGE = "dev.team.readtoday.client.oauth";

  JerseyConfig(AuthInfoProvider signUpUsernameProv,
               AuthController authController,
               JwtTokenReceiver jwtTokenReceiver) {
    packages(AUTH_SERVER_PACKAGE);
    register(new MyAbstractBinder(signUpUsernameProv, authController, jwtTokenReceiver));
  }

  private static final class MyAbstractBinder extends AbstractBinder {

    private final AuthInfoProvider signUpUsernameProv;
    private final AuthController authController;
    private final JwtTokenReceiver jwtTokenReceiver;

    MyAbstractBinder(AuthInfoProvider signUpUsernameProv,
                     AuthController authController,
                     JwtTokenReceiver jwtTokenReceiver) {
      this.signUpUsernameProv = signUpUsernameProv;
      this.authController = authController;
      this.jwtTokenReceiver = jwtTokenReceiver;
    }

    @Override
    protected void configure() {
      bind(signUpUsernameProv).to(AuthInfoProvider.class);
      bind(authController).to(AuthController.class);
      bind(jwtTokenReceiver).to(JwtTokenReceiver.class);
    }
  }
}
