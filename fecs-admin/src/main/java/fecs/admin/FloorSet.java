package fecs.admin;

import fecs.admin.model.Floor;
import org.springframework.beans.factory.InitializingBean;

import java.awt.*;
import java.util.HashSet;

/**
 * Created by jcooky on 2014. 3. 22..
 */
@org.springframework.stereotype.Component
public class FloorSet extends HashSet<Floor> implements InitializingBean {
  private static final Dimension FLOOR = new Dimension(150, 50);

  public Integer containsHeight(int y, int height) {
    for (Floor floor : this) {
      if (floor.containsHeight(y, height)) {
        return floor.getFloor();
      }
    }

    return null;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    int y = 0, numGroundFloors = 10;

    for (int i = -1 ; i < numGroundFloors ; ++i) {
      this.add(new Floor(i, new Rectangle(0, y, FLOOR.width, FLOOR.height)));

      y += FLOOR.height;
    }
  }
}
