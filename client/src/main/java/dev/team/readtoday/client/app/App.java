package dev.team.readtoday.client.app;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import dev.team.readtoday.client.app.gui.SceneContainer;
import dev.team.readtoday.client.app.gui.SceneType;
import dev.team.readtoday.client.app.jersey.JerseyHttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.auth.SignedOutEvent;
import dev.team.readtoday.client.usecase.auth.accesstoken.AccessTokenReceiver;
import dev.team.readtoday.client.usecase.auth.signin.SignInRequestListener;
import dev.team.readtoday.client.usecase.auth.signin.SuccessfulSignInEvent;
import dev.team.readtoday.client.usecase.auth.signup.SignUpRequestListener;
import dev.team.readtoday.client.usecase.auth.signup.SuccessfulSignUpEvent;
import dev.team.readtoday.client.usecase.channel.create.ChannelCreationListener;
import dev.team.readtoday.client.usecase.channel.search.SearchRequestListener;
import dev.team.readtoday.client.usecase.shared.AuthTokenSupplier;
import dev.team.readtoday.client.usecase.subscription.subscribe.SubscriptionListener;
import dev.team.readtoday.client.view.ViewController;
import dev.team.readtoday.client.view.admin.AdminView;
import dev.team.readtoday.client.view.auth.AuthView;
import dev.team.readtoday.client.view.home.HomeView;
import jakarta.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Application;
import javafx.stage.Stage;
import org.tomlj.TomlParseResult;

public final class App extends Application implements AuthTokenSupplier {

  private final ExecutorService eventBusExecutor = Executors.newCachedThreadPool();
  private final EventBus eventBus = new AsyncEventBus(eventBusExecutor);

  private Stage stage;

  private AccessTokenReceiver accessTokenReceiver;
  private String authToken;

  private SceneContainer sceneContainer;

  private final TomlParseResult config = ConfigurationLoader.load();

  @Override
  public void init() throws IOException {
    registerRequestListeners(this);
    eventBus.register(this);

    final AuthView authView = new AuthView(eventBus, buildGoogleAuthUrl());

    Map<SceneType, ViewController> controllers = Map.of(
        SceneType.AUTH, authView,
        SceneType.HOME, new HomeView(eventBus, Set.of()),
        SceneType.ADMIN, new AdminView(eventBus)
    );

    sceneContainer = new SceneContainer(eventBus, controllers, () -> stage);

    accessTokenReceiver = new AccessTokenReceiver(config, eventBus, authView);
  }

  private void registerRequestListeners(AuthTokenSupplier ats) {
    var factory = new JerseyHttpRequestBuilderFactory(config, ats);

    new SignUpRequestListener(eventBus, factory);
    new SignInRequestListener(eventBus, factory);
    new SearchRequestListener(eventBus, factory);
    new ChannelCreationListener(eventBus, factory);
    new SubscriptionListener(eventBus, factory);
  }

  @Override
  public void start(Stage stage) {
    this.stage = stage;

    stage.setOnHiding(event -> {
      accessTokenReceiver.close();
      eventBusExecutor.shutdownNow();
    });

    sceneContainer.setScene(SceneType.AUTH);
    stage.setTitle("ReadToday");
    stage.show();
  }

  @Subscribe
  public void onSuccessfulSignUp(SuccessfulSignUpEvent event) {
    authToken = event.getJwtToken();
    accessTokenReceiver.close();
  }

  @Subscribe
  public void onSuccessfulSignIn(SuccessfulSignInEvent event) {
    authToken = event.getJwtToken();
    accessTokenReceiver.close();
  }

  @Subscribe
  public void onSignedOut(SignedOutEvent event) {
    authToken = null;
    accessTokenReceiver.start();
  }

  @Override
  public String getAuthToken() {
    return authToken;
  }

  private URI buildGoogleAuthUrl() {
    String baseUri = config.getString("oauth.base_url");
    String clientId = config.getString("oauth.client_id");
    String redirectUri = config.getString("oauth.redirect_uri");
    return UriBuilder.fromUri(baseUri)
        .queryParam("client_id", clientId)
        .queryParam("redirect_uri", redirectUri)
        .build();
  }
}
