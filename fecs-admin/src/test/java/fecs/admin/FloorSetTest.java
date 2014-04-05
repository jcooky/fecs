package fecs.admin;

import fecs.admin.support.AbstractSpringBasedTestSupport;
import fecs.commons.model.Floor;
import fecs.commons.model.Passenger;
import fecs.commons.model.Vector;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by jcooky on 2014. 3. 23..
 */
public class FloorSetTest extends AbstractSpringBasedTestSupport {
  @Autowired
  private FloorSet floorSet;

  @Test
  public void testContainsHeight() throws Exception {

  }

  @Test
  public void testGet() throws Exception {
    for (int i = -1 ; i <= 10; ++i) {
      Floor floor = floorSet.get(i);
      if (i != 0) {
        assertNotNull(floor);
      } else {
        assertNull(floor);
      }
    }
  }

  @Test
  public void testAddPassenger() throws Exception {
    Passenger passenger = new Passenger();
    passenger.setState(Passenger.State.WAIT);
    passenger.setFloor(1);

    floorSet.addPassenger(Vector.UP, passenger);

    Floor floor = floorSet.get(1);
    assertTrue(floor.isPushed(Vector.UP));
    assertFalse(floor.isPushed(Vector.DOWN));

    Passenger target = floor.getPassengers().get(Vector.UP).take();
    assertNotNull(target);
    assertEquals(passenger, target);
  }
}
