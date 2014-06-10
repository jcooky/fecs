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

  private Integer howMany = 1, max = 30;

  private List<Passenger> passengers = new ArrayList<>();

  /* getters and setters */
  public Integer getNow() {
    return passengers.size();
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

  public void remove(Passenger p) {
    this.passengers.remove(p);
  }

  public void killRandom() {
    if (!passengers.isEmpty()) {
      int i = (int) (Math.random() * passengers.size());
      Passenger passenger = passengers.get(i);

      for (Cabin cabin : engine.getCabins().values()) {
        cabin.getPassengers().remove(passenger);
      }

      for (Floor floor : engine.getFloors().values()) {
        floor.getPassengers().remove(passenger);
      }

      this.passengers.remove(i);
    }
  }

  public void makePassenger() {
    for (int i = 0; i < howMany && getNow()< max; ++i) {
      FloorType start, dest;

      start = FloorType.FIRST;
      int rand =(int) (Math.random() * FloorType.values().length-1);
      dest = FloorType.values()[rand>0?rand+1:rand];

      Passenger passenger = new Passenger(this, start.getValue(), dest.getValue());
      passengers.add(passenger);
      engine.getFloors().get(FloorType.FIRST).add(passenger);
    }
  }

  @Override
  public Passenger makeFireFighter(Integer dest) {
    Passenger passenger = new Passenger(this, FloorType.FIRST.getValue(), dest);
    passengers.add(passenger);

    engine.getFloors().get(FloorType.FIRST).removeAll();
    engine.getFloors().get(FloorType.FIRST).add(passenger);

    return passenger;
  }

}
