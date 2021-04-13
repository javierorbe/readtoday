package dev.team.readtoday.client.app;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tomlj.Toml;
import org.tomlj.TomlParseResult;

enum ConfigurationLoader {
  ;

  private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationLoader.class);

  private static final String CONFIG_FILE = "config.toml";

  static TomlParseResult load() {
    try {
      Path path = getPathToResource(CONFIG_FILE);
      return loadFromPath(path);
    } catch (URISyntaxException | IOException e) {
      throw new IllegalStateException("Error loading configuration file.", e);
    }
  }

  private static Path getPathToResource(String filepath) throws URISyntaxException {
    ClassLoader loader = ConfigurationLoader.class.getClassLoader();
    URL url = loader.getResource(filepath);
    Objects.requireNonNull(url, "Invalid configuration file.");
    URI uri = url.toURI();
    return Path.of(uri);
  }

  private static TomlParseResult loadFromPath(Path path) throws IOException {
    TomlParseResult config = Toml.parse(path);
    config.errors().forEach(error -> LOGGER.error("Error parsing configuration file.", error));
    return config;
  }
}
