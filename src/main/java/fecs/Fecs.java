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
  public void run(String... args) {
    initJython();

    userInterface.run();
  }

  public static void main(String []args) throws IOException {
    SpringApplication app = new SpringApplication(Fecs.class);
    app.setHeadless(false);

    Fecs.applicationContext = app.run(args);
  }
  protected static final PythonInterpreter INTERP = new PythonInterpreter();
  public static PythonInterpreter getInterpreter(){return INTERP;}
  private void initJython(){
    INTERP.execfile(ClassLoader.getSystemResourceAsStream("__init__.py"));
    INTERP.set("ui", userInterface);
    INTERP.set("engine", engine);
    INTERP.set("passengerMaker", passengerMaker);
    INTERP.exec("DefaultCircumstance.ui=ui");
    INTERP.exec("DefaultCircumstance.engine=engine");
    INTERP.exec("DefaultCircumstance.passengerMaker=passengerMaker");
    INTERP.exec("DefaultCircumstance.crashCircumstance=CrashCircumstance");
    INTERP.set("default", Circumstance.get(CircumstanceType.DEFAULT));
    INTERP.exec("FireCircumstance.defaultCircumstance=default");
  }

  public static ApplicationContext getApplicationContext() { return applicationContext; }
}