package fecs.admin;

import fecs.admin.support.AbstractSpringBasedTestSupport;
import fecs.commons.model.Floor;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by jcooky on 2014. 3. 23..
 */
public class CabinsControllerTest extends AbstractSpringBasedTestSupport {
  @Autowired
  private CabinsController cabinsController;

  @Autowired
  private FloorSet floorSet;

  @Before
  public void setUp() {

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
