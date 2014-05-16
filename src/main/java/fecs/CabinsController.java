package fecs;

import fecs.model.Cabin;
import fecs.model.Floor;
import fecs.model.Vector;

/**
* Created by jcooky on 2014. 3. 23..
*/
public class CabinsController {

  public void control(Cabin left, Cabin right, Floor floor) {
    Cabin cabin = null;
    double dy_l = floor.getPosition() - left.getPosition(), dy_r = floor.getPosition() - right.getPosition();
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
            cabin = left.getQueue().size() < right.getQueue().size() ? left : right;
          }
        } else if (dy_l >= 0 && dy_r >= 0) {
          cabin = left.getQueue().size() < right.getQueue().size() ? left : right;
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
            cabin = left.getQueue().size() < right.getQueue().size() ? left : right;
          }
        } else if (dy_l <= 0 && dy_r <= 0) {
          cabin = left.getQueue().size() < right.getQueue().size() ? left : right;
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
            cabin = left.getQueue().size() < right.getQueue().size() ? left : right;
          }
        } else if (dy_l >= 0 && dy_r <= 0) {
          cabin = left.getQueue().size() < right.getQueue().size() ? left : right;
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
            cabin = left.getQueue().size() < right.getQueue().size() ? left : right;
          }
        } else if (dy_l <= 0 && dy_r >= 0) {
          cabin = left.getQueue().size() < right.getQueue().size() ? left : right;
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

}
