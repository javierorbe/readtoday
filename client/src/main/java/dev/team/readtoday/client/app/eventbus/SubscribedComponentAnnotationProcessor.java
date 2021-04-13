package dev.team.readtoday.client.app.eventbus;

import org.greenrobot.eventbus.EventBus;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
final class SubscribedComponentAnnotationProcessor implements BeanPostProcessor {

  private final ConfigurableListableBeanFactory beanFactory;

  SubscribedComponentAnnotationProcessor(ConfigurableListableBeanFactory beanFactory) {
    this.beanFactory = beanFactory;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) {
    Class<?> beanClass = bean.getClass();

    if (beanClass.isAnnotationPresent(SubscribedComponent.class)) {
      EventBus eventBus = beanFactory.getBean(EventBus.class);
      eventBus.register(bean);
    }

    return bean;
  }
}
