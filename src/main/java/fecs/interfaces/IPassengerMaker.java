package fecs.interfaces;

import fecs.simulator.Passenger;

/**
 * Created by Byoungwoo on 2014-05-16.
 */
public interface IPassengerMaker {
  /* getters and setters */
  Integer getNow();

  Integer getMax();

  void setMax(Integer val);

  Integer getHowMany();

  void setHowMany(Integer val);

  void makePassenger();

  Passenger makeFireFighter(Integer dest);
}
