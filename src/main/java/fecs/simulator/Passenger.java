package fecs.simulator;

import java.io.Serializable;

/**
 * Created by jcooky on 2014. 3. 23..
 */
public class Passenger implements Serializable {

  private State state = State.WAIT;
  private Integer dest;
  private Integer start;
//  static int curId=0;
//  private int id;

  public Passenger(Integer start, Integer dest) {
    this.dest = dest;
    this.start = start;
//    this.id=curId++;
  }

  public Integer getStart() {
    return start;
  }

  public void setStart(Integer start) {
    this.start = start;
  }

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public Integer getDest() {
    return dest;
  }

  public void setDest(Integer dest) {
    this.dest = dest;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Passenger passenger = (Passenger) o;

    if (dest != null ? !dest.equals(passenger.dest) : passenger.dest != null) return false;
    if (start != null ? !start.equals(passenger.start) : passenger.start != null) return false;
    if (state != passenger.state) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = state != null ? state.hashCode() : 0;
    result = 31 * result + (dest != null ? dest.hashCode() : 0);
    result = 31 * result + (start != null ? start.hashCode() : 0);
    return result;
  }


  public static enum State {
    WAIT,
    RIDING,
    NO_WAIT
  }

/*  @Override
  public int hashCode(){
    return this.id;
  }*/
}
