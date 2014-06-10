package fecs;

import fecs.interfaces.IPassengerMaker;
import fecs.model.CircumstanceType;
import fecs.simulator.Engine;
import fecs.ui.UserInterface;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Created by jcooky on 2014. 3. 22..
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "fecs")
public class Fecs implements CommandLineRunner{
  private static ConfigurableApplicationContext applicationContext;

  @Autowired
  private UserInterface userInterface;

  @Autowired
  private Engine engine;

  @Autowired
  private IPassengerMaker passengerMaker;

  @Override
  public void run(String... args) throws Exception {
    initJython();

    userInterface.run();
  }

  public static void main(String []args) throws IOException {
    SpringApplication app = new SpringApplication(Fecs.class);
    app.setHeadless(false);

    Fecs.applicationContext = app.run(args);
  }
  protected static final PythonInterpreter interp= new PythonInterpreter();
  public static PythonInterpreter getInterpreter(){return interp;}
  private void initJython(){
    interp.execfile(ClassLoader.getSystemResourceAsStream("__init__.py"));
    interp.set("ui", userInterface);
    interp.set("engine", engine);
    interp.set("passengerMaker",passengerMaker);
    interp.exec("DefaultCircumstance.ui=ui");
    interp.exec("DefaultCircumstance.engine=engine");
    interp.exec("DefaultCircumstance.passengerMaker=passengerMaker");
    interp.exec("DefaultCircumstance.crashCircumstance=CrashCircumstance");
    interp.set("default", Circumstance.get(CircumstanceType.DEFAULT));
    interp.exec("FireCircumstance.defaultCircumstance=default");
  }

  public static ApplicationContext getApplicationContext() { return applicationContext; }
}