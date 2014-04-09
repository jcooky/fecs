package fecs;

import fecs.config.RootConfig;
import fecs.ui.AdminUI;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.GenericApplicationContext;

import java.io.IOException;

/**
 * Created by jcooky on 2014. 3. 22..
 */
public class Main {
  public static GenericApplicationContext boot(GenericApplicationContext applicationContext) {
    for (ApplicationListener<?> listener : applicationContext.getBeansOfType(ApplicationListener.class).values()) {
      if (listener.getClass().getName().startsWith("fecs")) {
        applicationContext.addApplicationListener(listener);
      }
    }

    return applicationContext;
  }

  public static GenericApplicationContext boot() throws IOException {
    return boot(RootConfig.boot());
  }

  public static void main(String []args) throws IOException {
    GenericApplicationContext applicationContext = boot();

    applicationContext.getBean(AdminUI.class).run();
  }
}