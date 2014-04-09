package fecs;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jcooky on 2014. 4. 8..
 */
public abstract class Circumstance {

  public static final String DEFAULT = "Default";
  public static final String FIRE = "Fire";
  public static final String FLOOD = "Flood";
  public static final String CRASH = "Crash";
  public static final String EARTH_QUAKE = "EarthQuake";

  private static final Map<String, Circumstance> strategies = new HashMap<String, Circumstance>() {{
    this.put(FIRE, new FireCircumstance());
    this.put(FLOOD, new FloodCircumstance());
    this.put(CRASH, new CrashCircumstance());
    this.put(EARTH_QUAKE, new EarthQuakeCircumstance());
    this.put(DEFAULT, new DefaultCircumstance());
  }};

  protected long elapsedTime;
  protected String name;

  protected Circumstance(String name) {
    this.name = name;
  }

  public abstract void trigger() throws Exception;
  public abstract Circumstance setParameter(String key, Object val);

  public static Circumstance get(String name) {
    return strategies.get(name);
  }

  private static class DefaultCircumstance extends Circumstance {

    public DefaultCircumstance() {
      super(DEFAULT);
    }

    @Override
    public void trigger() throws Exception {
    }

    @Override
    public Circumstance setParameter(String key, Object val) {
      return this;
    }
  }

  private static class FireCircumstance extends Circumstance {

    protected FireCircumstance() {
      super(FIRE);
    }

    @Override
    public void trigger() throws Exception {

    }

    @Override
    public Circumstance setParameter(String key, Object val) {
      return this;
    }
  }

  private static class FloodCircumstance extends Circumstance {

    protected FloodCircumstance() {
      super(FLOOD);
    }

    @Override
    public void trigger() throws Exception {

    }

    @Override
    public Circumstance setParameter(String key, Object val) {
      return this;
    }
  }

  private static class CrashCircumstance extends Circumstance {

    protected CrashCircumstance() {
      super(CRASH);
    }

    @Override
    public void trigger() throws Exception {

    }

    @Override
    public Circumstance setParameter(String key, Object val) {
      return this;
    }
  }

  private static class EarthQuakeCircumstance extends Circumstance {

    protected EarthQuakeCircumstance() {
      super(EARTH_QUAKE);
    }

    @Override
    public void trigger() throws Exception {

    }

    @Override
    public Circumstance setParameter(String key, Object val) {
      return this;
    }
  }
}
