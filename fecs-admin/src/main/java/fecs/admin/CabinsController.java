package fecs.admin;

import fecs.commons.model.Cabin;
import fecs.commons.model.Floor;
import fecs.commons.model.Vector;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

/**
 * Created by jcooky on 2014. 3. 23..
 */
@Component
public class CabinsController implements InitializingBean {
  private static final Dimension SIZE = new Dimension(50, 50);

  private final Cabin left = new Cabin(new Rectangle2D.Double(0.0, 0.0, SIZE.getWidth(), SIZE.getHeight()));
  private final Cabin right = new Cabin(new Rectangle2D.Double(left.x + SIZE.getWidth() + 30.0, 0.0, SIZE.getWidth(), SIZE.getHeight()));
  private final Cabin both[] = new Cabin[]{left, right};

  @Autowired
  private FloorSet floorSet;

  public void target(Floor floor) {
    Cabin cabin = null;
    double dy_l = floor.y - left.y, dy_r = floor.y - right.y;
    if (Cabin.State.STOP.equals(left.getState()) && Cabin.State.STOP.equals(right.getState())) {
      // 둘 다 멈춰 있는 경우
      cabin = Math.abs(dy_l) <= Math.abs(dy_r) ? left : right;
    } else if (!Cabin.State.STOP.equals(left.getState()) && !Cabin.State.STOP.equals(right.getState())) {
      // 둘 다 움직이고 있는 경우
      if (Vector.UP.equals(left.getVector()) && Vector.UP.equals(right.getVector())) {

        if (dy_l < 0 && dy_r < 0) {
          if (Math.abs(dy_l) < Math.abs(dy_r)) {
            cabin = left;
          } else if (Math.abs(dy_l) > Math.abs(dy_r)) {
            cabin = right;
          } else {
            cabin = left.getNextSet().size() < right.getNextSet().size() ? left : right;
          }
        } else if (dy_l >= 0 && dy_r >= 0) {
          cabin = left.getNextSet().size() < right.getNextSet().size() ? left : right;
        } else if (dy_l < 0 && dy_r >= 0) {
          cabin = left;
        } else if (dy_l >= 0 && dy_r < 0) {
          cabin = right;
        }
      } else if (Vector.DOWN.equals(left.getVector()) && Vector.DOWN.equals(right.getVector())) {
        if (dy_l > 0 && dy_r > 0) {
          if (Math.abs(dy_l) < Math.abs(dy_r)) {
            cabin = left;
          } else if (Math.abs(dy_l) > Math.abs(dy_r)) {
            cabin = right;
          } else {
            cabin = left.getNextSet().size() < right.getNextSet().size() ? left : right;
          }
        } else if (dy_l <= 0 && dy_r <= 0) {
          cabin = left.getNextSet().size() < right.getNextSet().size() ? left : right;
        } else if (dy_l > 0 && dy_r <= 0) {
          cabin = left;
        } else if (dy_l <= 0 && dy_r > 0) {
          cabin = right;
        }
      } else if (Vector.UP.equals(left.getVector()) && Vector.DOWN.equals(right.getVector())) {
        if (dy_l < 0 && dy_r > 0) {
          if (Math.abs(dy_l) < Math.abs(dy_r)) {
            cabin = left;
          } else if (Math.abs(dy_l) > Math.abs(dy_r)) {
            cabin = right;
          } else {
            cabin = left.getNextSet().size() < right.getNextSet().size() ? left : right;
          }
        } else if (dy_l >= 0 && dy_r <= 0) {
          cabin = left.getNextSet().size() < right.getNextSet().size() ? left : right;
        } else if (dy_l < 0 && dy_r <= 0) {
          cabin = left;
        } else if (dy_l >= 0 && dy_r > 0) {
          cabin = right;
        }
      } else if (Vector.DOWN.equals(left.getVector()) && Vector.UP.equals(right.getVector())) {
        if (dy_l > 0 && dy_r < 0) {
          if (Math.abs(dy_l) < Math.abs(dy_r)) {
            cabin = left;
          } else if (Math.abs(dy_l) > Math.abs(dy_r)) {
            cabin = right;
          } else {
            cabin = left.getNextSet().size() < right.getNextSet().size() ? left : right;
          }
        } else if (dy_l <= 0 && dy_r >= 0) {
          cabin = left.getNextSet().size() < right.getNextSet().size() ? left : right;
        } else if (dy_l > 0 && dy_r >= 0) {
          cabin = left;
        } else if (dy_l <= 0 && dy_r < 0) {
          cabin = right;
        }
      }
    } else {
      // 한 쪽 만 멈춰 있는 경우
      cabin = Cabin.State.STOP.equals(left.getState()) ? left : right;
    }
    cabin.setTarget(floor);
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    Floor floor = floorSet.get(1);

    for (Cabin cabin : getBoth()) {
      cabin.y = floor.y;
    }
  }

  @Override
  public String toString() {
    return "CabinsController{" +
        "floorSet=" + floorSet +
        "cabins=" + Arrays.asList(getBoth()).toString() +
        '}';
  }

  public Cabin[] getBoth() {
    return both;
  }
}
