package fecs.simulator;

import fecs.model.Vector;

import java.io.Serializable;

/**
 * Created by jcooky on 2014. 3. 22..
 */
public class Floor extends Place implements Serializable {
  public static final int WIDTH = 150;
  public static final int HEIGHT = 50;

  private Integer num;
  private Double wait;

  public Floor(Integer num) {
    this.num = num;
  }

  public void add(Passenger passenger) {
    Vector vector = passenger.getDest() > passenger.getStart() ? Vector.UP : Vector.DOWN;

    super.passengers.add(passenger);
  }

  public boolean containsHeight(int y, int height) {
    return position <= y && position + HEIGHT >= y + height;
  }

  public int getNum() {
    return num;
  }
}
