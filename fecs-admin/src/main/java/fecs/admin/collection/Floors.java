package fecs.admin.collection;

import fecs.admin.model.Floor;

import java.awt.*;
import java.util.HashSet;

/**
 * Created by jcooky on 2014. 3. 22..
 */
public class Floors extends HashSet<Floor> {
  private static final Dimension FLOOR = new Dimension(150, 50);

  public Floors(int numFloors) {
    super(numFloors);
  }

  public Integer containsHeight(int y, int height) {
    for (Floor floor : this) {
      if (floor.containsHeight(y, height)) {
        return floor.getFloor();
      }
    }

    return null;
  }

  public static Floors DEFAULT() {
    int y = 0, numGroundFloors = 10, numUndergrloudFloors = 1;

    Floors floors = new Floors(numGroundFloors + numUndergrloudFloors);

    for (int i = -1 ; i < numGroundFloors ; ++i) {
      floors.add(new Floor(i, new Rectangle(0, y, FLOOR.width, FLOOR.height)));

      y += FLOOR.height;
    }

    return floors;
  }
}
