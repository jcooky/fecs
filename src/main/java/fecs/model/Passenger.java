package fecs.model;

import java.io.Serializable;

/**
 * Created by jcooky on 2014. 3. 23..
 */
public class Passenger implements Serializable {

  private State state = State.WAIT;
  private Integer dest;
  private Integer start;

  public Passenger(Integer dest, Integer start) {
    this.dest = dest;
    this.start = start;
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

  public static enum State {
    WAIT,
    RIDING,
    NO_WAIT
  }
}
