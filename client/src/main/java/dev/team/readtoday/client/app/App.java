package dev.team.readtoday.client.app;

import dev.team.readtoday.client.app.gui.SceneContainer;
import dev.team.readtoday.client.app.gui.SceneType;
import dev.team.readtoday.client.usecase.auth.SignedOutEvent;
import dev.team.readtoday.client.usecase.auth.accesstoken.AccessTokenReceiver;
import dev.team.readtoday.client.usecase.auth.signin.SuccessfulSignInEvent;
import dev.team.readtoday.client.usecase.auth.signup.SuccessfulSignUpEvent;
import dev.team.readtoday.client.usecase.shared.AuthTokenSupplier;
import dev.team.readtoday.client.view.ViewController;
import dev.team.readtoday.client.view.admin.AdminView;
import dev.team.readtoday.client.view.auth.AuthView;
import dev.team.readtoday.client.view.home.HomeView;
import jakarta.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.tomlj.TomlParseResult;

public final class App extends Application implements AuthTokenSupplier {

  private final TomlParseResult config = ConfigurationLoader.load();

  private final ExecutorService eventBusExecutor = Executors.newCachedThreadPool();
  private final EventBus eventBus;

  private final AppContext context;

  private Stage stage;
  private final SceneContainer sceneContainer;

  private final AccessTokenReceiver accessTokenReceiver;
  private String authToken;

  public App() {
    eventBus = EventBus.builder().executorService(eventBusExecutor).eventInheritance(false).build();
    final AuthView authView = new AuthView(eventBus, buildGoogleAuthUrl());

    Map<SceneType, ViewController> controllers = Map.of(
        SceneType.AUTH, authView,
        SceneType.HOME, new HomeView(eventBus, Set.of()),
        SceneType.ADMIN, new AdminView(eventBus)
    );

    sceneContainer = new SceneContainer(eventBus, controllers, () -> stage);
    accessTokenReceiver = new AccessTokenReceiver(config, eventBus, authView);

    context = new AppContext(eventBus, config, this);

    eventBus.register(this);
  }

  @Override
  public void start(Stage primaryStage) {
    this.stage = primaryStage;

    primaryStage.setOnHiding(event -> {
      accessTokenReceiver.close();
      context.close();
      eventBusExecutor.shutdownNow();
      Platform.exit();
    });

    sceneContainer.setScene(SceneType.AUTH);
    primaryStage.setTitle("ReadToday");
    primaryStage.show();
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
