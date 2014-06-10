package fecs.model;

/**
 * Created by jcooky on 2014. 3. 23..
 */
public enum Vector {
  UP(-1), DOWN(1);

  private int value;

  private Vector(int value) {
    this.value = value;
  }
  public int value(){ return this.value; }
}
