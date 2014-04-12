package fecs.admin.support;

import fecs.Main;
import fecs.config.RootConfig;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by jcooky on 2014. 3. 23..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootConfig.class })
public abstract class AbstractSpringBasedTestSupport {
  @Autowired
  protected GenericApplicationContext applicationContext;

  @Rule
  public TestWatcher watcher = new TestWatcher() {
    @Override
    protected void starting(Description description) {
      Main.boot(applicationContext);
    }
  };
}
