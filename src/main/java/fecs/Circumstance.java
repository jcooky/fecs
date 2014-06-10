package fecs;

import fecs.interfaces.ICircumstance;
import fecs.model.CircumstanceType;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyString;

import java.util.EnumMap;
import java.util.Map;
/**
 * Created by jcooky on 2014. 4. 8..
 */

public abstract class Circumstance implements ICircumstance {
  private static final Map<CircumstanceType, Circumstance> strategies = new EnumMap<CircumstanceType, Circumstance>(CircumstanceType.class) {
    {
      this.put(CircumstanceType.FIRE, new FireCircumstance());
      this.put(CircumstanceType.FLOOD,new FloodCircumstance());
      this.put(CircumstanceType.CRASH,new CrashCircumstance());
      this.put(CircumstanceType.EARTH_QUAKE,new EarthQuakeCircumstance());
      this.put(CircumstanceType.DEFAULT,new DefaultCircumstance());
    }
  };
  private CircumstanceType type;
  private PyObject pyCircumstance;
  protected Circumstance(CircumstanceType type) {
    pyCircumstance = Fecs.getInterpreter().get(type.type()+"Circumstance");
    this.type = type;
  }

  public void trigger() {
    pyCircumstance.__getattr__("trigger").__call__(); //executes .py file
  }

  public Circumstance setParameter(String key, Object val){
    pyCircumstance.__getattr__("setParameter").__call__(new PyString(key), Py.java2py(val));
    return this;
  }

  public Object getParameter(String key) {
    PyObject m = pyCircumstance.__getattr__("getParameter");
    if(m==null)
      {return null;}
    return m.__call__(new PyString(key)).__tojava__(Object.class);
  }

  public static Circumstance get(CircumstanceType type) {
    return strategies.get(type);
  }

  private static class DefaultCircumstance extends Circumstance  {
    public DefaultCircumstance() {
      super(CircumstanceType.DEFAULT);
    }
  }
  private static class FireCircumstance extends Circumstance {
    protected FireCircumstance() {
      super(CircumstanceType.FIRE);
    }
  }
  private static class FloodCircumstance extends Circumstance {
    protected FloodCircumstance() {
      super(CircumstanceType.FLOOD);
    }
  }
  private static class CrashCircumstance extends Circumstance {
    protected CrashCircumstance() {
      super(CircumstanceType.CRASH);
    }
  }
  private static class EarthQuakeCircumstance extends Circumstance {
    protected EarthQuakeCircumstance() {
      super(CircumstanceType.EARTH_QUAKE);
    }
  }
}
