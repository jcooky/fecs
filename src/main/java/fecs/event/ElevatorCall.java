package fecs.event;

import fecs.simulator.Passenger;
import fecs.model.Vector;
import org.springframework.context.ApplicationEvent;

/**
 * Created by jcooky on 2014. 3. 23..
 */
public class ElevatorCall extends ApplicationEvent {

  private Vector pushed = null;

  /**
   * Create a new ApplicationEvent.
   *
   * @param source the component that published the event (never {@code null})
   */
  public ElevatorCall(Passenger source, Vector pushed) {
    super(source);

    this.pushed = pushed;
  }

  public Passenger getSource() {
    return (Passenger)source;
  }

  public Vector getPushed() {
    return pushed;
  }
}
