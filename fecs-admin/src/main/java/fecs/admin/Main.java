package fecs.admin;

import fecs.admin.config.AdminConfig;
import fecs.admin.ui.AdminUI;
import fecs.commons.config.RootConfig;
import org.springframework.context.support.GenericApplicationContext;

/**
 * Created by jcooky on 2014. 3. 22..
 */
public class Main {
  public static void main(String []args) {
    GenericApplicationContext applicationContext = RootConfig.boot(AdminConfig.class);
    AdminUI adminUI = applicationContext.getBean(AdminUI.class);
    adminUI.run();
  }
}
