package fecs.simulator;

import java.io.Serializable;

/**
 * Created by jcooky on 2014. 3. 22..
 */
public class Floor extends Place implements Serializable{//,Comparable {
  public static final int PIXEL_WIDTH = 150,PIXEL_HEIGHT = 50,
    REAL_HEIGHT=3;

  private Integer num;

  public Floor(Integer num) {
    this.num = num;
  }

  public void add(Passenger passenger) {
    super.passengers.add(passenger);
  }

  public boolean containsHeight(int y, int height) {
    return position <= y && position + PIXEL_HEIGHT >= y + height;
  }

  public Integer getNum() {
    return num;
  }

  /*@Override
  public int compareTo(Object o) {
    Floor f = (Floor) o;
    return f.getNum()>this.getNum()?1:-1;
  }*/

  @Override
  public String toString() {
    return "Floor{" +
        "num=" + num +
        "} " + super.toString();
  }
}
