package fecs.commons.model;

import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by jcooky on 2014. 3. 22..
 */
public class Floor extends Rectangle2D.Double implements Serializable {
  private int floor;
  private Map<Vector, BlockingQueue<Passenger>> passengers = new EnumMap<Vector, BlockingQueue<Passenger>>(Vector.class){{
    this.put(Vector.UP, new LinkedBlockingQueue<Passenger>());
    this.put(Vector.DOWN, new LinkedBlockingQueue<Passenger>());
  }};

  public Floor(int floor, Rectangle2D.Double rect) {
    super(rect.x, rect.y, rect.width, rect.height);

    this.floor = floor;
  }

  public int getFloor() {
    return floor;
  }

  public Map<Vector, BlockingQueue<Passenger>> getPassengers() {
    return passengers;
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
}
