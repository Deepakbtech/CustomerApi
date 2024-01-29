package com.abnamro.test.customer.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextHolder implements ApplicationContextAware {

  private static ApplicationContext applicationContext;
  private static AutowireCapableBeanFactory factory;

  public static <T> T getBean(Class<T> beanClass) {
    return applicationContext.getBean(beanClass);
  }

  public static <T> void autowireBean(T bean) {
    factory.autowireBean(bean);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    ApplicationContextHolder.applicationContext = applicationContext;
    factory = applicationContext.getAutowireCapableBeanFactory();
  }
}
