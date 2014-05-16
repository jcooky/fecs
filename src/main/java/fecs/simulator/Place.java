package fecs.simulator;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by jcooky on 2014. 5. 16..
 */
public class Place {
  protected Set<Passenger> passengers=new TreeSet<Passenger>();
  protected double position;
  protected String name;

  public Set<Passenger> getPassengers() {
    return passengers;
  }
  public void killPassengers() {
    this.passengers.clear();
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
}
