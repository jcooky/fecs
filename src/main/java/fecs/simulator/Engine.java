package fecs.simulator;

import fecs.Circumstance;
import fecs.interfaces.IEngine;
import fecs.model.CabinType;
import fecs.model.FloorType;
import fecs.model.Vector;
import fecs.ui.Renderer;
import fecs.ui.UserInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jcooky on 2014. 5. 12..
 */
@Component("engine")
public class Engine implements IEngine, Runnable, InitializingBean {
  private static final double TARGET_WIDTH = 300;
  private static final double TARGET_HEIGHT = 550;

  private static final double ACCEL = 0.1;

  @Autowired
  private UserInterface ui;

  @Autowired
  private PassengerMaker passengerMaker;

  private final Logger logger = LoggerFactory.getLogger(Engine.class);

  private Long lastUpdateTime = System.currentTimeMillis();

  /* gravity related constants */
  public static final Double earthGravity = 9.80665d;
  public static final Double[] gravityTable = new Double[]{earthGravity, 1.622d, 3.711d, 8.87d}; // 지구, 달, 화성, 금성

  private Double gravity = earthGravity;
  private Map<CabinType, Cabin> cabins = new EnumMap<>(CabinType.class);
  private Map<FloorType, Floor> floors = new EnumMap<>(FloorType.class);
  private Double cabinWeight = 700d;
  private Integer state = STATE_STOP;
  private Double forceBreak = 10000d;
  private Double motorOutput = 27000d;
  private Integer cabinLimitPeople = 12;
  private Double cabinLimitWeight = 1660d; // = cabinLimitPeople*passengerWeight+cabinWeight;
  private Double moreEnterProbability = 0.22;
  private Double passengerWeight = 80d;

  @Override
  public void run() {
    try {
      Long currentTime = System.currentTimeMillis();
      double deltaTime = (currentTime - lastUpdateTime) * 0.001;

        int s = state & 1; //get last bit
        if (s == Engine.STATE_START) { //last bit is 1 = started
          s = (state & ~1) >> 1; //get the others bit
          if (s >= Circumstance.CircumstanceVector.length) throw new Exception("unstable state value");
          Circumstance.get(Circumstance.CircumstanceVector[s])
              .setParameter("currentTime", currentTime)
              .setParameter("deltaTime", deltaTime)
              .trigger();
          for (Cabin cabin : cabins.values()) updateCabin(cabin, deltaTime);
        }

        lastUpdateTime = currentTime;

      draw();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    } finally {
      SwingUtilities.invokeLater(this);
    }
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    this.initFloors();
    this.initCabins();
  }

  private void draw() {
      Renderer renderer = ui.getRenderer();
      Graphics g = renderer.getGraphics();
      JPanel target = ui.getDrawTarget();

      g.setColor(Color.GRAY);
      g.fillRect(0, 0, target.getWidth(), target.getHeight());

      Map<CabinType, Integer> xmap = new HashMap<CabinType, Integer>() {{
        this.put(CabinType.LEFT, Floor.WIDTH + 10);
        this.put(CabinType.RIGHT, Floor.WIDTH + 10 + Cabin.WIDTH + 30);
      }};

      for (CabinType type : cabins.keySet()) {
        Integer x = xmap.get(type);
        Cabin cabin = cabins.get(type);

        Integer passengers = new Integer(cabin.getPassengers().size());

        g.setColor(Color.WHITE);
        g.fillRect(x, (int) cabin.getPosition(), Cabin.WIDTH, Cabin.HEIGHT);
        g.setColor(Color.BLACK);
        g.drawRect(x, (int) cabin.getPosition(), Cabin.WIDTH, Cabin.HEIGHT);
        g.drawString(passengers.toString(), (int) ((double) x + ((double) (Cabin.WIDTH) / 2.0) - ((double) (g.getFont().getSize()) / 2.0)),
            (int) (cabin.getPosition() + ((double) (Cabin.HEIGHT) / 2.0)));
      }

      for (Floor floor : floors.values()) {
        Integer passengers = new Integer(floor.getPassengers().size());

        g.setFont(Font.getFont(Font.SANS_SERIF));
        g.setColor(Color.WHITE);
        g.fillRect(1, (int) floor.getPosition(), Floor.WIDTH, Floor.HEIGHT);
        g.setColor(Color.BLACK);
        g.drawRect(1, (int) floor.getPosition(), Floor.WIDTH, Floor.HEIGHT);
        g.drawString(floor.getNum() + "층", 1, (int) floor.getPosition() + 15);
        g.drawString(passengers.toString(), (int) (1.0 + ((double) (Floor.WIDTH) / 2.0) - ((double) (g.getFont().getSize()) / 2.0)),
            (int) (floor.getPosition() + ((double) (Floor.HEIGHT) / 2.0)));
      }

      renderer.flush();
  }

  public void updateCabin(Cabin cabin, double accel, double deltaTime) {
    cabin.setVelocity(cabin.getVelocity() + accel * deltaTime);
    cabin.setPosition(cabin.getPosition() + (cabin.getVelocity() * deltaTime) * 16.6667);
  }

  private void updateCabin(Cabin cabin, double deltaTime) {
    if (cabin.isOn()) {
      Vector vector = cabin.getVector();
      double motor = motorOutput * (vector == null ? 0 : vector == Vector.DOWN ? 1.0 : -1.0);
      double accel = (motor) / mass(cabin) + gravity;

      Floor target = cabin.getTarget();
      switch (cabin.getState()) {
        case MOVE:
          updateCabin(cabin, accel, deltaTime);
          if ((vector == Vector.DOWN && cabin.getPosition() > target.getPosition())
              || (vector == Vector.UP && cabin.getPosition() < target.getPosition())) {
            cabin.setPosition(target.getPosition());
            cabin.stop();

            if (!cabin.getQueue().isEmpty()) {
              cabin.move();
            }
          }
          break;
        case STOP:
          cabin.stop();
          break;
      }
    }
  }

  private void initCabins() {
    cabins.put(CabinType.LEFT, new Cabin());
    cabins.put(CabinType.RIGHT, new Cabin());
  }

  private void initFloors() throws Exception {
    double y = 0;
    int numGroundFloors = 10;

    for (int i = numGroundFloors; i >= -1; --i) {
      if (i != 0) {
        Floor floor = new Floor(i);
        floor.setPosition(y);

        floors.put(FloorType.valueOf(i), floor);

        y += Floor.HEIGHT;
      }
    }
  }

  public Double mass(Cabin cabin) {
    return ((cabinWeight / earthGravity) + ((passengerWeight / earthGravity) * cabin.getPassengers().size()));
  }

  public Double getGravity() {
    return gravity;
  }

  public void setGravity(Double gravity) {
    this.gravity = gravity;
  }

  public Double getCabinWeight() {
    return cabinWeight;
  }

  public void setCabinWeight(Double cabinWeight) {
    this.cabinWeight = cabinWeight;
  }

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    if ((state | 1) == 1) this.state &= state;
    this.state = state;
  }

  public Double getForceBreak() {
    return forceBreak;
  }

  public void setForceBreak(Double forceBreak) {
    this.forceBreak = forceBreak;
  }

  public Double getMotorOutput() {
    return motorOutput;
  }

  public void setMotorOutput(Double motorOutput) {
    this.motorOutput = motorOutput;
  }

  public Integer getCabinLimitPeople() {
    return cabinLimitPeople;
  }

  public void setCabinLimitPeople(Integer cabinLimitPeople) {
    this.cabinLimitPeople = cabinLimitPeople;
  }

  public Double getCabinLimitWeight() {
    return cabinLimitWeight;
  }

  public void setCabinLimitWeight(Double cabinLimitWeight) {
    this.cabinLimitWeight = cabinLimitWeight;
  }

  public Double getMoreEnterProbability() {
    return moreEnterProbability;
  }

  public void setMoreEnterProbability(Double moreEnterProbability) {
    this.moreEnterProbability = moreEnterProbability;
  }

  public Double getPassengerWeight() {
    return passengerWeight;
  }

  public void setPassengerWeight(Double passengerWeight) {
    this.passengerWeight = passengerWeight;
  }


//  public static double toPixelPosX(float posX) {
//    return TARGET_WIDTH * posX / 100.f;
//  }
//
//  public static float toPosX(double posX) {
//    return (float) ((posX * 100.0 * 1.0) / TARGET_WIDTH);
//  }
//
//  public static double toPixelPosY(float posY) {
//    double h = TARGET_HEIGHT;
//    return h - (1.0f * h) * posY / 100.0f;
//  }
//
//  public static float toPosY(double posY) {
//    return (float) (100.0 - ((posY * 100.0 * 1.0) / TARGET_HEIGHT));
//  }
//
//  public static double toPixelWidth(float width) {
//    return TARGET_WIDTH * width / 100.0f;
//  }
//
//  public static double toPixelHeight(float height) {
//    return TARGET_HEIGHT * height / 100.0f;
//  }

  public Map<FloorType, Floor> getFloors() {
    return floors;
  }

  public Map<CabinType, Cabin> getCabins() {
    return cabins;
  }
}
