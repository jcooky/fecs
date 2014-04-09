package fecs.model;

import java.io.Serializable;

/**
 * Created by jcooky on 2014. 3. 23..
 */
public class Passenger implements Serializable {
  public static enum State {
    WAIT,
    RIDING,
    NO_WAIT
  }

  private State state;
  private Integer floor;

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public Integer getFloor() {
    return floor;
  }

  public void setFloor(Integer floor) {
    this.floor = floor;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Passenger passenger = (Passenger) o;

    if (floor != null ? !floor.equals(passenger.floor) : passenger.floor != null) return false;
    if (state != passenger.state) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = state != null ? state.hashCode() : 0;
    result = 31 * result + (floor != null ? floor.hashCode() : 0);
    return result;
  }
}
