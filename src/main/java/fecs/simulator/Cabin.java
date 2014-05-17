package fecs.simulator;

import java.io.Serializable;
import java.util.*;

/**
 * Created by jcooky on 2014. 3. 23..
 */
public class Cabin extends Place implements Serializable {

  public static final int WIDTH = 50;
  public static final int HEIGHT = 50;

  private PriorityQueue<Floor> queue = new PriorityQueue<>(new TreeSet<>(new NextSetComparator()));

  private boolean on = false;

  private State state = State.STOP;
  private Floor target = null;
  private fecs.model.Vector vector = null;
  private double velocity = 0.0;

  private void move(Floor floor) {
    if (State.MOVE.equals(state)) {
      queue.add(floor);
    } else {
      this.state = State.MOVE;
      target = floor;
      vector = target.position > this.position ? fecs.model.Vector.DOWN : fecs.model.Vector.UP;
    }
  }

  public void move() {
    this.move(queue.poll());
  }

  public void stop() {
    this.state = State.STOP;
    this.vector = null;
    this.target = null;
    this.velocity = 0;
  }

  public double getVelocity() {
    return velocity;
  }

  public void setVelocity(double velocity) {
    this.velocity = velocity;
  }

  public PriorityQueue<Floor> getQueue() {
    return queue;
  }

  public void setTarget(Floor floor) {
    move(floor);
  }

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public fecs.model.Vector getVector() {
    return vector;
  }

  public Floor getTarget() {
    return target;
  }

  public void disable() {
    this.on = false;
  }

  public void enable() {
    this.on = true;
  }

  public boolean isOn() {
    return on;
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
