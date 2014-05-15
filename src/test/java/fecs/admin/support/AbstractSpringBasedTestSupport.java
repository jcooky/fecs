package fecs.admin.support;

import fecs.Fecs;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by jcooky on 2014. 3. 23..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Fecs.class })
public abstract class AbstractSpringBasedTestSupport {
  @Autowired
  protected GenericApplicationContext applicationContext;

}
