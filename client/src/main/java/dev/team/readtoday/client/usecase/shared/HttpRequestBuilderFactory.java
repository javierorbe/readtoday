package dev.team.readtoday.client.usecase.shared;

public interface HttpRequestBuilderFactory {

  /**
   * Build a {@link HttpRequestBuilder} for a path, without authentication header.
   *
   * @param path the path of the request
   * @return a {@code HttpRequestBuilder} for the request
   */
  HttpRequestBuilder build(String path);

  /**
   * Build a {@link HttpRequestBuilder} for a path, with authentication header.
   *
   * @param path the path of the request
   * @return a {@code HttpRequestBuilder} for the request
   */
  HttpRequestBuilder buildWithAuth(String path);
}
