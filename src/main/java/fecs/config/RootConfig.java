package fecs.config;

import fecs.ui.AdminUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

/**
 * Created by jcooky on 2014. 3. 22..
 */
@Configuration
@ComponentScan("fecs")
public class RootConfig {

  @Autowired
  private ApplicationContext applicationContext;

  @Bean
  public AdminUI adminUI() {
    return new AdminUI(applicationContext);
  }

  public static GenericApplicationContext boot() {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(RootConfig.class);

    return applicationContext;
  }

}
