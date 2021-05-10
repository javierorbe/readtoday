package dev.team.readtoday.client.usecase.category.create;

import dev.team.readtoday.client.app.eventbus.SubscribedComponent;
import dev.team.readtoday.client.usecase.category.create.events.CategoryCreationEvent;
import dev.team.readtoday.client.usecase.category.create.events.CategoryCreationFailedEvent;
import dev.team.readtoday.client.usecase.category.create.events.CategorySuccessfullyCreatedEvent;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The CategoryCreationListener class listen for category creation events. When \ref
 * events.CategoryCreationEvent "CategoryCreationEvent" event is received, a POST HTTP request to
 * create a category will be sent to readtoday server.
 * <p>
 * If category is created successfully a \ref events.CategorySuccessfullyCreatedEvent
 * "CategorySuccessfullyCreatedEvent" event will be posted, otherwise an \ref
 * events.CategoryCreationFailedEvent "CategorySuccessfullyCreatedEvent" event will be posted by
 * eventbus.
 *
 * @author Aldayr Ruiz
 */
@SubscribedComponent
public final class CategoryCreationListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(CategoryCreationListener.class);

  private final EventBus eventBus;
  private final HttpRequestBuilder requestBuilder;

  public CategoryCreationListener(EventBus eventBus, HttpRequestBuilderFactory factory) {
    this.eventBus = eventBus;
    requestBuilder = factory.buildWithAuth("/categories");
  }

  @Subscribe(threadMode = ThreadMode.ASYNC)
  public void onCategoryCreationRequestReceived(CategoryCreationEvent event) {
    LOGGER.trace("Sending category creation request.");
    HttpResponse response = requestBuilder.post(event.getRequest());
    if (response.isStatusCreated()) {
      LOGGER.trace("Category created successfully response received.");
      eventBus.post(new CategorySuccessfullyCreatedEvent());
    } else {
      LOGGER.trace("Category failed on creation response received.");
      eventBus.post(new CategoryCreationFailedEvent(response.getStatusReason()));
    }
  }
}
