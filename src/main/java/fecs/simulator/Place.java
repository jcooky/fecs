package fecs.simulator;

import java.util.Set;

/**
 * Created by jcooky on 2014. 5. 16..
 */
public class Place {
  private Set<Passenger> passengers;
  protected double position;
  protected String name;

  public Set<Passenger> getPassengers() {
    return passengers;
  }

  public void setPassengers(Set<Passenger> passengers) {
    this.passengers = passengers;
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
