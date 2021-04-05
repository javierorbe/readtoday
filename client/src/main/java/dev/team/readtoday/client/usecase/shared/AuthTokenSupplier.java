package dev.team.readtoday.client.usecase.shared;

@FunctionalInterface
public interface AuthTokenSupplier {

  String getAuthToken();
}
