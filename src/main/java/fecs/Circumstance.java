package fecs;

import fecs.interfaces.ICircumstance;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyString;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by jcooky on 2014. 4. 8..
 */
public abstract class Circumstance implements ICircumstance {

  private static final Map<String, Circumstance> strategies = new HashMap<String, Circumstance>() {
    {
      this.put(FIRE, new FireCircumstance());
      this.put(FLOOD,new FloodCircumstance());
      this.put(CRASH,new CrashCircumstance());
      this.put(EARTH_QUAKE,new EarthQuakeCircumstance());
      this.put(DEFAULT,new DefaultCircumstance());
    }
  };
  protected long elapsedTime;
  protected String name;
  private PyObject pyCircumstance;
  protected Circumstance(String name) {
    pyCircumstance = Fecs.interp.get(name+"Circumstance");
    this.name = name;
  }

  public void trigger() throws Exception{
    pyCircumstance.__getattr__("trigger").__call__(); //executes .py file
  }

//  Map<String,Object> param=new HashMap<String,Object>();
  public Circumstance setParameter(String key, Object val){
//    param.put(key,val);
    pyCircumstance.__getattr__("setParameter").__call__(new PyString(key), Py.java2py(val));
    return this;
  }

  public static Circumstance get(String name) {
    return strategies.get(name);
  }

  private static class DefaultCircumstance extends Circumstance  {
    public DefaultCircumstance() {
      super(DEFAULT);
    }
  }
  private static class FireCircumstance extends Circumstance {
    protected FireCircumstance() {
      super(FIRE);
    }
  }
  private static class FloodCircumstance extends Circumstance {
    protected FloodCircumstance() {
      super(FLOOD);
    }
  }
  private static class CrashCircumstance extends Circumstance {
    protected CrashCircumstance() {
      super(CRASH);
    }
  }
  private static class EarthQuakeCircumstance extends Circumstance {
    protected EarthQuakeCircumstance() {
      super(EARTH_QUAKE);
    }
  }
}
