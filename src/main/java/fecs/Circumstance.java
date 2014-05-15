package fecs;

import java.util.HashMap;
import java.util.Map;

import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.*;
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

  private static PythonInterpreter interp;
  private PyObject pyObject; // where each circumstances gets python object

  protected long elapsedTime;
  protected String name;

  protected Circumstance(String name) {
    this.name = name;
    if(null==interp) {
      interp = new PythonInterpreter();
      interp.exec("from fecs import *");
    }
    this.pyObject = interp.get(name+"Circumstance"); //use name value as those of jython class names
  }

  public void trigger() throws Exception{
    PyObject rst = pyObject.__call__();
    if(!(Boolean)rst.__tojava__(Boolean.class))
      throw new Exception("something went wrong with jython");
  };
  public Circumstance setParameter(String key, Object val){
    pyObject.__call__(new PyString(key), Py.java2py(val));
    return this;
  };

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
