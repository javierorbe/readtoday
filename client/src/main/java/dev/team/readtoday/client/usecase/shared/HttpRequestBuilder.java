package dev.team.readtoday.client.usecase.shared;

public interface HttpRequestBuilder {

  HttpRequestBuilder withParam(String name, Object... values);

  HttpResponse get();

  HttpResponse post(Object entity);
}
