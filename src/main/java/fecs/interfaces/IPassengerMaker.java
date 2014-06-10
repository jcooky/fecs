package fecs.interfaces;

/**
 * Created by Byoungwoo on 2014-05-16.
 */
public interface IPassengerMaker {
  /* getters and setters */
  Integer getNow();

  void setNow(Integer now);

  Integer getMax();

  void setMax(Integer val);

  Integer getHowMany();

  void setHowMany(Integer val);

  void makePassenger();
}
