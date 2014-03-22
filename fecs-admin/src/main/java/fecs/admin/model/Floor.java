package fecs.admin.model;

import java.awt.*;

/**
 * Created by jcooky on 2014. 3. 22..
 */
public class Floor extends Rectangle {
  private int floor;

  public Floor(int floor, Rectangle rectangle) {
    super(rectangle);

    this.floor = floor;
  }

  public int getFloor() {
    return floor;
  }

  public boolean containsHeight(int y, int height) {
    return this.y <= y && this.y + this.height >= y + height;
  }
}
