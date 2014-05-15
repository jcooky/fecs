package fecs.model;

import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Created by jcooky on 2014. 3. 22..
 */
public class Floor extends Rectangle2D.Double implements Serializable {
  public static double WIDTH = 150.0;
  public static double HEIGHT = 50.0;

  private int floor;
  private Map<Vector, Queue<Passenger>> passengers = new EnumMap<Vector, Queue<Passenger>>(Vector.class){{
    this.put(Vector.UP, new LinkedList<Passenger>());
    this.put(Vector.DOWN, new LinkedList<Passenger>());
  }};

  public Floor(int floor, Rectangle2D.Double rect) {
    super(rect.x, rect.y, rect.width, rect.height);

    this.floor = floor;
  }

  public int getFloor() {
    return floor;
  }

  public Queue<Passenger> getPassengers(Vector key) {
    return passengers.get(key);
  }

  public boolean containsHeight(int y, int height) {
    return this.y <= y && this.y + this.height >= y + height;
  }

  public boolean isPushed(Vector vector) {
    switch(vector) {
      case DOWN: return !this.passengers.get(Vector.DOWN).isEmpty();
      case UP: return !this.passengers.get(Vector.UP).isEmpty();
      default: throw new RuntimeException("Unknown vector parameter");
    }
  }

  public void add(Passenger passenger) {
    Vector vector = passenger.getDest() - passenger.getStart() > 0 ? Vector.UP : Vector.DOWN;

    passengers.get(vector).add(passenger);
  }

  public void update() {

  }
}
