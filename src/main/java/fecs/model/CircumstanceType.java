package fecs.model;

/**
 * Created by jcooky on 2014. 6. 11..
 */
public enum CircumstanceType {
  DEFAULT("Default", 1), FIRE("Fire", 2), FLOOD("Flood", 3),
  CRASH("Crash", 4), EARTH_QUAKE("EarthQuake", 5);

  private String name;
  private int value;

  CircumstanceType(String name, int value) {
    this.name = name;
    this.value = value;
  }

  public String type() {
    return name;
  }

  public int state() {
    return value;
  }

  public static CircumstanceType valueOf(int val) {
    for (CircumstanceType type : values()) {
      if (val == type.value) {
        return type;
      }
    }

    throw new IllegalArgumentException();
  }
}
