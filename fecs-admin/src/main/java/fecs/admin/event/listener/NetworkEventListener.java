package fecs.admin.event.listener;

import fecs.admin.Server;
import fecs.commons.event.NetworkEventWrapper;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by jcooky on 2014. 3. 23..
 */
@Component
public class NetworkEventListener implements ApplicationListener<NetworkEventWrapper> {
  @Autowired
  private Server server;

  @Override
  public void onApplicationEvent(NetworkEventWrapper event) {
    for (IoSession session : server.getAcceptor().getManagedSessions().values()) {
      session.write(event.getSource());
    }
  }
}
