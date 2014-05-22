package fecs.simulator;

import fecs.Fecs;
import fecs.model.FloorType;

import java.io.Serializable;
import java.util.*;

/**
 * Created by jcooky on 2014. 3. 23..
 */
public class Cabin extends Place implements Serializable {

  public static final int PIXEL_WIDTH = 50, PIXEL_HEIGHT = 50, REAL_WIDTH=3, REAL_HEIGHT=3;

//  private Set<Floor> queue = new HashSet<>();
  private Queue<Floor> queue = new LinkedList<>();

  private boolean on = false;

  private State state = State.STOP;
  private Floor target = null;
  private double vector = 0d,
    velocity = 0d;

  public void move(Floor floor) {
    if (State.MOVE.equals(state)) {
      queue.add(floor);
    } else {
      this.state = State.MOVE;
      setTarget(floor);
    }
  }

  public void move() {
    /*Set<Floor> floors = new HashSet<>(queue);
    double maxVector=0d;
    Floor maxVectorFloor=null;
    for(Floor f : floors){
      double vec = f.getPosition() - position;
      Double vectorSum = Math.abs(vector + vec);//Math.abs(position-f.getPosition());
      if(maxVectorFloor==null || maxVector<vectorSum) // initialize // near floor first
        maxVectorFloor = f;
      else if(maxVector==vectorSum && maxVectorFloor.getNum()>f.getNum())// lower floor first
        maxVectorFloor = f;
      else continue;
        maxVector = vec;
    }*/

//    if(maxVectorFloor!=null) {
//      target=maxVectorFloor;
    Floor f = queue.poll();
    if(f!=null)
      this.move(f);
//    }
  }

  public void stop() {
    this.state = State.STOP;
//    this.target = null;
    this.velocity = 0;
  }

  public double getVelocity() {
    return velocity;
  }

  public void setVelocity(double velocity) {
    this.velocity = velocity;
  }

  public Queue<Floor> getQueue() {
    return queue;
  }

  public void setTarget(Floor floor) {
    this.target = floor;
    this.vector = floor==null? 0d : floor.getPosition()-position;
  }

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public double getVector() { return vector; }

  public void setVector(double vector) { this.vector = vector; }

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

  public static enum State {
    STOP,
    MOVE
  }
}
