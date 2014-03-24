package fecs.commons.model;

import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by jcooky on 2014. 3. 23..
 */
public class Cabin extends Rectangle2D.Double implements Serializable {

  public static enum State {
    STOP,
    MOVE
  }

  private static final double ACCEL = 0.1;

  private SortedSet<Floor> nextSet = new TreeSet<Floor>(new Comparator<Floor>() {
    @Override
    public int compare(Floor o1, Floor o2) {
      return o1.getFloor() - o2.getFloor();
    }
  });

  private State state = State.STOP;
  private Floor target = null;
  private Vector vector = null;

  private long lastUpdateTime = System.currentTimeMillis();

  public Cabin(Rectangle2D.Double rect) {
    super(rect.x, rect.y, rect.width, rect.height);
  }

  public SortedSet<Floor> getNextSet() {
    return nextSet;
  }

  public void setTarget(Floor floor) {
    move(floor, this.state);
  }

  private void move(Floor floor, State state) {
    if (State.MOVE.equals(state)) {
      nextSet.add(floor);
    } else {
      this.state = State.MOVE;
      target = floor;
      vector = target.y > this.y ? Vector.DOWN : Vector.UP;
    }
  }

  private void stop() {
    this.state = State.STOP;
    this.vector = null;
    this.target = null;
  }

  public void update() {
    long curtime = System.currentTimeMillis(), deltaTime = curtime - lastUpdateTime;
    double accel = ACCEL * (this.vector == Vector.DOWN ? 1.0 : -1.0);

    switch(state) {
      case MOVE:
        this.y += (accel * deltaTime * deltaTime)/2.0;
        if ((this.vector == Vector.DOWN && this.y > target.y)
            || (this.vector == Vector.UP && this.y < target.y)) {
          this.y = target.y;

          if (nextSet.isEmpty()) {
            stop();
          } else {
            move(nextSet.first(), State.STOP);
          }
        }
        break;
      case STOP:
        stop();
        break;
    }

    lastUpdateTime = curtime;
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
}
