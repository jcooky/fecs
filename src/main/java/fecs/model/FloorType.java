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
    switch (i) {
      case -1: return UNDER_FIRST;
      case 1: return FIRST;
      case 2: return SECOND;
      case 3: return THIRD;
      case 4: return FOURTH;
      case 5: return FIFTH;
      case 6: return SIXTH;
      case 7: return SEVENTH;
      case 8: return EIGHTH;
      case 9: return NINETH;
      case 10: return TENTH;
      default: throw new IllegalArgumentException("i is illegal[i: " + i + "]");
    }
  }
}
