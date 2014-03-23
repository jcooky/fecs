package fecs.admin;

import fecs.admin.config.AdminConfig;
import fecs.admin.ui.AdminUI;
import fecs.commons.config.RootConfig;
import org.springframework.context.support.GenericApplicationContext;

import java.io.IOException;

/**
 * Created by jcooky on 2014. 3. 22..
 */
public class Main {

  public static void main(String []args) throws IOException {
    GenericApplicationContext applicationContext = RootConfig.boot(AdminConfig.class);
    applicationContext.getBean(AdminUI.class).run();
    applicationContext.getBean(Server.class).serve();
  }
}
