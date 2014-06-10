package fecs.simulator;

import fecs.util.Function;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by jcooky on 2014. 5. 16..
 */
public abstract class Place {
  protected Set<Passenger> passengers=new HashSet<Passenger>();
  protected double position;
  protected String name;

  public Set<Passenger> getPassengers() {
    return passengers;
  }

  public void killPassengers() {
    removeAll();
  }

  public void removeAll() {
    removeAll(new Function<Boolean, Passenger>() {
      @Override
      public Boolean f(Passenger arg) {
        return true;
      }
    });
  }

  public void removeUsingFilterNoWait() {
    removeAll(new Function<Boolean, Passenger>() {
      @Override
      public Boolean f(Passenger arg) {
        return arg.getState() == Passenger.State.NO_WAIT;
      }
    });
  }

  private void removeAll(Function<Boolean, Passenger> function) {
    Iterator<Passenger> it = passengers.iterator();
    while (it.hasNext()) {
      Passenger p = it.next();
      if (function.f(p)) {
        p.remove();
        it.remove();
      }
    }
  }

  public double getPosition() {
    return position;
  }

  public void setPosition(double position) {
    this.position = position;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Place{" +
        "passengers=" + passengers +
        ", position=" + position +
        ", name='" + name + '\'' +
        '}';
  }
}
