package dev.team.readtoday.client.view.settings;

import dev.team.readtoday.client.model.NotificationPreference;
import dev.team.readtoday.client.model.Settings;
import dev.team.readtoday.client.usecase.settings.update.FailedSettingsUpdate;
import dev.team.readtoday.client.usecase.settings.update.SettingsSuccessfullyUpdated;
import dev.team.readtoday.client.usecase.settings.update.UpdateSettingsEvent;
import dev.team.readtoday.client.view.AlertLauncher;
import dev.team.readtoday.client.view.ViewController;
import java.net.URL;
import java.time.ZoneId;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public final class SettingsView implements ViewController, Initializable {

  @FXML
  private ChoiceBox<NotificationPreference> emailFrequency;

  @FXML
  private ChoiceBox<String> zone;

  private final Settings settings;

  private final EventBus eventBus;

  public SettingsView(EventBus eventBus, Settings settings) {
    eventBus.register(this);
    this.eventBus = eventBus;
    this.settings = settings;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Objects.requireNonNull(emailFrequency);
    Objects.requireNonNull(zone);

    // Load zones + email frequencies
    zone.getItems().setAll(getAvailableZones());
    emailFrequency.getItems().setAll(NotificationPreference.values());

    // Set user values returned from server
    zone.setValue(settings.getZoneId().toString());
    emailFrequency.setValue(settings.getNotificationPreference());
  }

  private ObservableList<String> getAvailableZones() {
    return FXCollections.observableArrayList(ZoneId.SHORT_IDS.values().stream().toList());
  }

  @FXML
  public void saveSettings() {
    ZoneId zoneId = ZoneId.of(zone.getValue());
    eventBus.post(new UpdateSettingsEvent(zoneId, emailFrequency.getValue()));
  }

  @Subscribe
  public void onSuccessfulSettingsUpdated(SettingsSuccessfullyUpdated event) {
    AlertLauncher.info("Settings were updated");
  }

  @Subscribe
  public void onSettingsUpdatedFailed(FailedSettingsUpdate event) {
    AlertLauncher.error("An error occurred. Reason: " + event.getReason());
  }
}
