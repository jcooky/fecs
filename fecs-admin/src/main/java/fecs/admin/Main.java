package fecs.admin;

import fecs.admin.config.AdminConfig;
import fecs.admin.event.listener.NetworkEventListener;
import fecs.admin.ui.AdminUI;
import fecs.commons.config.RootConfig;
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
    applicationContext.addApplicationListener(applicationContext.getBean(NetworkEventListener.class));

    return applicationContext;
  }

  public static GenericApplicationContext boot() throws IOException {
    return boot(RootConfig.boot(AdminConfig.class));
  }

  public static void main(String []args) throws IOException {
    GenericApplicationContext applicationContext = boot();

    applicationContext.getBean(AdminUI.class).run();
    applicationContext.getBean(Server.class).serve();
  }
}
