package fecs;

import fecs.event.ElevatorCall;
import fecs.model.Floor;
import fecs.model.Passenger;
import fecs.model.Vector;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;

/**
 * Created by jcooky on 2014. 3. 22..
 */
@org.springframework.stereotype.Component
public class FloorSet extends HashSet<Floor> implements InitializingBean, SmartApplicationListener {
  private static final Dimension FLOOR = new Dimension(150, 50);


  @Autowired
  private CabinsController cabinsController;

  public Integer containsHeight(int y, int height) {
    for (Floor floor : this) {
      if (floor.containsHeight(y, height)) {
        return floor.getFloor();
      }
    }

    return null;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    double y = 0;
    int numGroundFloors = 10;

    for (int i = numGroundFloors ; i >= -1 ; --i) {
      if (i != 0) {
        this.add(new Floor(i, new Rectangle2D.Double(0.0, y, FLOOR.getWidth(), FLOOR.getHeight())));

        y += FLOOR.getHeight();
      }
    }
  }

  public Floor get(int n) {
    for (Floor floor : this ) {
      if (floor.getFloor() == n)
        return floor;
    }

    return null;
  }

  public void addPassenger(Vector vector, Passenger passenger) throws InterruptedException {
    if (Passenger.State.WAIT.equals(passenger.getState())) {
      Floor floor = get(passenger.getFloor());
      floor.getPassengers().get(vector).put(passenger);
      cabinsController.target(floor);
    }
  }

  @Override
  public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
    return ElevatorCall.class.equals(eventType);
  }

  @Override
  public boolean supportsSourceType(Class<?> sourceType) {
    return true;
  }

  @Override
  public void onApplicationEvent(ApplicationEvent applicationEvent) {
    if (applicationEvent instanceof ElevatorCall) {
      ElevatorCall event = (ElevatorCall)applicationEvent;
      try {
        addPassenger(event.getPushed(), event.getSource());
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  public int getOrder() {
    return 0;
  }
}
