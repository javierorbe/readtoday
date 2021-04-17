package dev.team.readtoday.client.usecase.shared;

public interface HttpResponse {

  /** Status code 200. */
  boolean isStatusOk();

  /** Status code 201. */
  boolean isStatusCreated();

  /** Status code 204. */
  boolean isStatusNoContent();

  String getStatusReason();

  /** Return the content entity as a certain type. */
  <T> T getEntity(Class<T> clazz);

  <T> T getEntity(GenericType<T> type);
}
