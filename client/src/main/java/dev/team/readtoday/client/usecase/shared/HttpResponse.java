package dev.team.readtoday.client.usecase.shared;

public interface HttpResponse {

  boolean isStatusOk();

  boolean isStatusCreated();

  String getStatusReason();

  /** Return the content entity as a certain type. */
  <T> T getEntity(Class<T> clazz);
}
