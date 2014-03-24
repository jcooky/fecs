package fecs.commons.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by jcooky on 2014. 3. 23..
 */
public class NetworkEventWrapper extends ApplicationEvent {

  /**
   * Create a new ApplicationEvent.
   *
   * @param source the component that published the event (never {@code null})
   */
  public NetworkEventWrapper(ApplicationEvent source) {
    super(source);
  }

  public ApplicationEvent getSource() {
    return (ApplicationEvent)source;
  }
}
