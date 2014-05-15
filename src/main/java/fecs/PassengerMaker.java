package fecs;

import fecs.model.FloorType;
import fecs.model.Passenger;
import fecs.physics.Engine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Byoungwoo on 2014-05-13.
 */
@Component
public class PassengerMaker {
  @Autowired
  private Engine engine;

  private Integer now = 0, howMany = 10, max = 30;

  private Set<Passenger> passengers = new HashSet<Passenger>();
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

  public void update(long curTime) {
    if (lastUpdateTime == null || lastUpdateTime - curTime > 1000.0) {
      for (int i = 0; i < howMany; ++i) {
        FloorType start, dest;

        do {
          start = FloorType.values()[(int) (Math.random() * FloorType.values().length)];
          dest = FloorType.values()[(int) (Math.random() * FloorType.values().length)];
        } while(start == dest);

        Passenger passenger = new Passenger(start.getValue(), dest.getValue());

        engine.getFloors().get(FloorType.FIRST).add(passenger);
      }

      lastUpdateTime = curTime;
    }

    now += howMany;
  }

}
