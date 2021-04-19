package dev.team.readtoday.client.usecase.category.search;

import com.google.common.collect.ImmutableCollection;
import dev.team.readtoday.client.app.eventbus.SubscribedComponent;
import dev.team.readtoday.client.model.Category;
import dev.team.readtoday.client.usecase.category.search.events.SearchAllCategoriesEvent;
import dev.team.readtoday.client.usecase.category.search.events.SearchAllCategoriesFailedEvent;
import dev.team.readtoday.client.usecase.category.search.events.SearchAllCategoriesSuccessfullyEvent;
import dev.team.readtoday.client.usecase.category.search.messages.AllCategoryResponse;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilder;
import dev.team.readtoday.client.usecase.shared.HttpRequestBuilderFactory;
import dev.team.readtoday.client.usecase.shared.HttpResponse;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SubscribedComponent
public final class CategorySearchAllListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(CategorySearchAllListener.class);

  private final EventBus eventBus;
  private final HttpRequestBuilder requestBuilder;

  public CategorySearchAllListener(EventBus eventBus, HttpRequestBuilderFactory factory) {
    this.eventBus = eventBus;
    requestBuilder = factory.buildWithAuth("/categories");
  }

  @Subscribe(threadMode = ThreadMode.ASYNC)
  public void onSearchAllCategoriesRequestReceived(SearchAllCategoriesEvent event) {
    LOGGER.trace("Sending search all categories request.");
    HttpResponse response = requestBuilder.get();

    if (response.isStatusOk()) {
      LOGGER.trace("Successfully search all categories response received.");
      AllCategoryResponse entity = response.getEntity(AllCategoryResponse.class);
      ImmutableCollection<Category> categories = entity.toCategoriesCollection();
      eventBus.post(new SearchAllCategoriesSuccessfullyEvent(categories));
    } else {
      LOGGER.trace("Search all categories failed response received.");
      eventBus.post(new SearchAllCategoriesFailedEvent(response.getStatusReason()));
    }
  }
}
