package fecs.interfaces;

/**
 * Created by Byoungwoo on 2014-05-14.
 */
public interface ICircumstance {
  public static final String DEFAULT = "Default"; //1
  public static final String FIRE = "Fire"; //2
  public static final String FLOOD = "Flood"; //3
  public static final String CRASH = "Crash"; //4
  public static final String EARTH_QUAKE = "EarthQuake"; //5
  /* commented numbers above used for bitwise strategy selection using array below */
  public static final String[] CIRCUMSTANCE_VECTOR = new String[] {DEFAULT,FIRE,FLOOD,CRASH,EARTH_QUAKE};

  public static final int STATE_DEFAULT = 1, STATE_FIRE = 2, STATE_FLOOD = 3, STATE_CRASH = 4, STATE_EARTH_QUAKE = 5;

  public void trigger();
  public fecs.Circumstance setParameter(String key,Object val);
}
