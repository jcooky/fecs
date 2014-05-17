package fecs.simulator;

import fecs.interfaces.IPassengerMaker;
import fecs.model.FloorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Byoungwoo on 2014-05-13.
 */
@Component("passengerMaker")
public class PassengerMaker implements IPassengerMaker {
  @Autowired
  private Engine engine;

  private Integer now = 0, howMany = 10, max = 30;

  private Long lastUpdateTime = null;

  private List<Passenger> passengers = new ArrayList<>();

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

  public void killRandom() {
    int i = (int)(Math.random() * passengers.size());
    Passenger passenger = passengers.get(i);

    for (Cabin cabin : engine.getCabins().values()) {
      cabin.getPassengers().remove(passenger);
    }

    for (Floor floor : engine.getFloors().values()) {
      floor.getPassengers().remove(passenger);
    }

    this.passengers.remove(i);
  }

  public void makePassenger() {
    for (int i = 0; i < howMany; ++i) {
      FloorType start, dest;

      start = FloorType.FIRST;
      int rand =(int) (Math.random() * FloorType.values().length-1);
      dest = FloorType.values()[rand>0?rand+1:rand];

      Passenger passenger = new Passenger(start.getValue(), dest.getValue());

      engine.getFloors().get(FloorType.FIRST).add(passenger);
    }

    now += howMany;
  }

}
