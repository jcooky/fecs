package fecs;

import fecs.simulator.Cabin;
import fecs.simulator.Floor;
/**
* Created by jcooky on 2014. 3. 23..
*/
public class CabinsController {
  private CabinsController(){}
  public static void control(Cabin left, Cabin right, Floor floor) {
    Cabin cabin = null;
    final double DY_L = floor.getPosition() - left.getPosition(), DY_R = floor.getPosition() - right.getPosition();
    if (Cabin.State.STOP.equals(left.getState()) && Cabin.State.STOP.equals(right.getState())) {
      // 둘 다 멈춰 있는 경우
      cabin = Math.abs(DY_L) <= Math.abs(DY_R) ? left : right;
    } else if (!Cabin.State.STOP.equals(left.getState()) && !Cabin.State.STOP.equals(right.getState())) {
      // 둘 다 움직이고 있는 경우
      return;
/*      if (Vector.UP.equals(left.getVector()) && Vector.UP.equals(right.getVector())) {

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
      }*/
    } else {
      // 한 쪽 만 멈춰 있는 경우
      Cabin stoppedCabin = Cabin.State.STOP.equals(left.getState()) ? left : right,
        movingCabin = stoppedCabin.equals(right) ? left : right;
      if(movingCabin.getTarget()==null
          || movingCabin.getTarget().equals(floor))
        {return;}
      else cabin = stoppedCabin;
    }
    cabin.move(floor);
  }

}
