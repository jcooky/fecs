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

  public static FloorType valueOf(int i) {
    for (FloorType floorType : values()) {
      if (i == floorType.getValue()) {
        return floorType;
      }
    }

    throw new IllegalArgumentException("i is illegal[i: " + i + "]");
  }
}
