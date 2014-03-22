package fecs.commons.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

import java.util.Arrays;

/**
 * Created by jcooky on 2014. 3. 22..
 */
@Configuration
@ComponentScan("fecs")
public class RootConfig {

  public static GenericApplicationContext boot(Class<?> ...annotatedClasses) {
    annotatedClasses = Arrays.copyOf(annotatedClasses, annotatedClasses.length+1);
    annotatedClasses[annotatedClasses.length-1] = RootConfig.class;

    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(annotatedClasses);

    return applicationContext;
  }
}
