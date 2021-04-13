package dev.team.readtoday.client.usecase.shared;

public interface HttpRequestBuilder {

  /** Add a query param to the request. */
  HttpRequestBuilder withParam(String name, Object... values);

  /** Get request. */
  HttpResponse get();

  /** Post request. */
  HttpResponse post(Object entity);

  /** Delete request. */
  HttpResponse delete(String document);
}
