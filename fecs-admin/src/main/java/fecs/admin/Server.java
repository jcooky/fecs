package fecs.admin;

import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.net.InetSocketAddress;
import java.util.LinkedHashMap;

/**
 * Created by jcooky on 2014. 3. 23..
 */
@Component
public class Server extends IoHandlerAdapter {
  @Autowired
  private ApplicationContext applicationContext;

  private NioSocketAcceptor acceptor;

  public NioSocketAcceptor getAcceptor() {
    return acceptor;
  }

  @Override
  public void messageReceived(IoSession session, Object message) throws Exception {
    if (message instanceof Throwable) {
      Throwable e = (Throwable)message;
      e.printStackTrace();
    } else if (message instanceof ApplicationEvent) {
      ApplicationEvent event = (ApplicationEvent)message;
      applicationContext.publishEvent(event);
    } else {
      throw new InvalidObjectException("Unknown message(class: " + message.getClass() + ")");
    }
  }

  @Override
  public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
    session.write(cause);
  }

  public void serve() throws IOException {
    acceptor = new NioSocketAcceptor();
//    NioSocketConnector acceptor = new NioSocketConnector();
    acceptor.setReuseAddress(true);

    acceptor.getFilterChain().setFilters(new LinkedHashMap<String, IoFilter>() {{
      put("object", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
    }});

    acceptor.setHandler(this);
    acceptor.bind(new InetSocketAddress("0.0.0.0", 4321));
//    acceptor.connect()
  }
}
