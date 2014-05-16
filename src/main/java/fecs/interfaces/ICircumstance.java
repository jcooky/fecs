package fecs.interfaces;

import fecs.Circumstance;

/**
 * Created by Byoungwoo on 2014-05-14.
 */
public interface ICircumstance {
  public static final String DEFAULT = "Default";
  public static final String FIRE = "Fire";
  public static final String FLOOD = "Flood";
  public static final String CRASH = "Crash";
  public static final String EARTH_QUAKE = "EarthQuake";

  public void trigger() throws Exception;
  public Circumstance setParameter(String key,Object val);
}
