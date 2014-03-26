package fecs.admin;

import fecs.admin.support.AbstractSpringBasedTestSupport;
import fecs.commons.model.Floor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;

/**
 * Created by jcooky on 2014. 3. 23..
 */
public class CabinsControllerTest extends AbstractSpringBasedTestSupport {
  @Autowired
  @Spy
  private CabinsController cabinsController;

  @Autowired
  @Spy
  private FloorSet floorSet;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    cabinsController.setLeft(cabinsController);
  }

  @Test
  public void testMove() {
    // given
    Floor floor = floorSet.get(10);

    // when
    cabinsController.target(floor);

    // then
  }
}
