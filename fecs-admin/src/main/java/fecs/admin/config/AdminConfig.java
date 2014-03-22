package fecs.admin.config;

import fecs.admin.ui.AdminUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jcooky on 2014. 3. 22..
 */
@Configuration
public class AdminConfig {
  @Autowired
  private ApplicationContext applicationContext;

  @Bean
  public AdminUI adminUI() {
    return new AdminUI(applicationContext);
  }
}
