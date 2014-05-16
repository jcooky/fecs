package fecs.model;

import java.io.Serializable;
import java.util.*;

/**
 * Created by jcooky on 2014. 3. 23..
 */
public class Cabin extends Place implements Serializable {

  public static final int WIDTH = 50;
  public static final int HEIGHT = 50;


  private PriorityQueue<Floor> queue = new PriorityQueue<>(new TreeSet<>(new NextSetComparator()));
  private Set<Passenger> passengers = new HashSet<>();
  private boolean isOn = false;

  private State state = State.STOP;
  private Floor target = null;
  private Vector vector = null;

  public void move(Floor floor, State state) {
    if (State.MOVE.equals(state)) {
      queue.add(floor);
    } else {
      this.state = State.MOVE;
      target = floor;
      vector = target.position > this.position ? Vector.DOWN : Vector.UP;
    }
  }

  public void stop() {
    this.state = State.STOP;
    this.vector = null;
    this.target = null;
  }

  public void killPassengers() {
    this.passengers.clear();
  }

  public PriorityQueue<Floor> getQueue() {
    return queue;
  }

  public void setTarget(Floor floor) {
    move(floor, this.state);
  }

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public Vector getVector() {
    return vector;
  }

  public Floor getTarget() {
    return target;
  }

  public void disable() {
    this.isOn = false;
  }

  public void enable() {
    this.isOn = true;
  }

  private static class NextSetComparator implements Comparator<Floor> {

    @Override
    public int compare(Floor o1, Floor o2) {
      return o1.getNum() - o2.getNum();
    }
  }

  public static enum State {
    STOP,
    MOVE
  }
}
