package fecs.interfaces;

import fecs.Circumstance;

import java.util.Vector;

/**
 * Created by Byoungwoo on 2014-05-14.
 */
public interface ICircumstance {
  public static final String DEFAULT = "Default"; //0
  public static final String FIRE = "Fire"; //1
  public static final String FLOOD = "Flood"; //2
  public static final String CRASH = "Crash"; //3
  public static final String EARTH_QUAKE = "EarthQuake"; //4
  /* commented numbers above used for bitwise strategy selection using array below */
  public static final String[] CircumstanceVector = new String[] {DEFAULT,FIRE,FLOOD,CRASH,EARTH_QUAKE};

  public void trigger() throws Exception;
  public Circumstance setParameter(String key,Object val);
}
