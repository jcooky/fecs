package fecs.model;

/**
 * Created by jcooky on 2014. 5. 12..
 */
public enum FloorType {
  UNDER_FIRST(-1), FIRST(1), SECOND(2), THIRD(3), FOURTH(4), FIFTH(5), SIXTH(6), SEVENTH(7), EIGHTH(8), NINETH(9), TENTH(10);

  private int value;
  private FloorType(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  private static FloorType arr[] = {FIRST,SECOND,THIRD,FOURTH,FIFTH,SIXTH,SEVENTH,EIGHTH,NINETH,TENTH};
  public static FloorType valueOf(int i) {
    if (i==-1) return UNDER_FIRST;
    if (1<=i && i <=10) return arr[i-1];
    throw new IllegalArgumentException("i is illegal[i: " + i + "]");
  }
}
