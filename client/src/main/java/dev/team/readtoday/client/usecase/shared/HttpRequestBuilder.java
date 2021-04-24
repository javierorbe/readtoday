package dev.team.readtoday.client.usecase.shared;

public interface HttpRequestBuilder {

  /**
   * Add a query param to the request.
   */
  HttpRequestBuilder withParam(String name, Object... values);

  /**
   * Add a path to the request
   */
  HttpRequestBuilder path(String path);

  /**
   * Get request.
   */
  HttpResponse get();

  /**
   * Post request.
   */
  HttpResponse post(Object entity);

  /**
   * Put request
   */
  HttpResponse put(Object entity);

  /**
   * Delete request.
   */
  HttpResponse delete(String document);
}
