package fecs.simulator;

import fecs.model.FloorType;
import fecs.simulator.Passenger;
import fecs.simulator.Engine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Byoungwoo on 2014-05-13.
 */
@Component
public class PassengerMaker {
  @Autowired
  private Engine engine;

  private Integer now = 0, howMany = 10, max = 30;

  private Long lastUpdateTime = null;

  /* getters and setters */
  public Integer getNow() {
    return now;
  }

  public void setNow(Integer now) {
    this.now = now;
  }

  public Integer getMax() {
    return max;
  }

  public void setMax(Integer val) {
    max = val;
  }

  public Integer getHowMany() {
    return howMany;
  }

  public void setHowMany(Integer val) {
    howMany = val;
  }

  public void makePassenger() {
    for (int i = 0; i < howMany; ++i) {
      FloorType start, dest;

      do {
        start = FloorType.values()[(int) (Math.random() * FloorType.values().length)];
        dest = FloorType.values()[(int) (Math.random() * FloorType.values().length)];
      } while (start == dest);

      Passenger passenger = new Passenger(start.getValue(), dest.getValue());

      engine.getFloors().get(FloorType.FIRST).add(passenger);
    }

    now += howMany;
  }

}
