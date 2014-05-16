package fecs;

import fecs.interfaces.IFecs;
import fecs.interfaces.IPassengerMaker;
import fecs.ui.UserInterface;
import org.apache.commons.lang3.ArrayUtils;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Created by jcooky on 2014. 3. 22..
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "fecs")
public class Fecs implements CommandLineRunner,IFecs{
  @Autowired
  private UserInterface userInterface;

  @Autowired
  private IPassengerMaker passengerMaker;

  @Override
  public void run(String... args) throws Exception {
    Circumstance.get(Circumstance.FIRE).setParameter("passengerMaker", ArrayUtils.toArray(passengerMaker));

    userInterface.run();
  }

  public static void main(String []args) throws IOException {
    SpringApplication app = new SpringApplication(Fecs.class);
    app.setHeadless(false);

    initJython();
    app.run(args);
  }
  protected static PythonInterpreter interp;
  private static void initJython(){
    interp=new PythonInterpreter();
    interp.execfile("__init__.py");
  }
}