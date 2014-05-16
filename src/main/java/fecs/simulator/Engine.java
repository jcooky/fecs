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
import java.util.Map;

/**
 * Created by jcooky on 2014. 5. 12..
 */
@Component
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
  private Double gravity = 9.8;
  private Map<CabinType, Cabin> cabins = new EnumMap<>(CabinType.class);
  private Map<FloorType, Floor> floors = new EnumMap<>(FloorType.class);
  private Double cabinWeight = 1.0;
  private Integer state = STATE_STOP;
  private Double forceBreak = 9.8;
  private Double motorOutput = 10.0;
  private Integer cabinLimitPeople = 30;
  private Double cabinLimitWeight = 23.0;
  private Double moreEnterProbability = 0.22;
  private Double passengerWeight = 1.0;

  @Override
  public void run() {
    try {
      Long currentTime = System.currentTimeMillis(), deltaTime = lastUpdateTime = System.currentTimeMillis() - lastUpdateTime;
      int s = state & STATE_START;

      if (s != 0) {

        for (Cabin cabin : cabins.values()) updateCabin(cabin, deltaTime);

        if ((state & STATE_FIRE) != 0) {

        } else {
          Circumstance.get("Default").trigger();
        }
      }

      draw();

      lastUpdateTime = currentTime;
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

    Cabin cabin = cabins.get(CabinType.LEFT);
    g.setColor(Color.WHITE);
    g.fillRect((int) (Floor.WIDTH + 10.0), (int) cabin.getPosition(), Cabin.WIDTH, Cabin.HEIGHT);
    g.setColor(Color.BLACK);
    g.drawRect((int) (Floor.WIDTH + 10.0), (int) cabin.getPosition(), Cabin.WIDTH, Cabin.HEIGHT);

    cabin = cabins.get(CabinType.RIGHT);
    g.setColor(Color.WHITE);
    g.fillRect((int) (Floor.WIDTH + 10.0 + Cabin.WIDTH + 30.0), (int) cabin.getPosition(), Cabin.WIDTH, Cabin.HEIGHT);
    g.setColor(Color.BLACK);
    g.drawRect((int) (Floor.WIDTH + 10.0 + Cabin.WIDTH + 30.0), (int) cabin.getPosition(), Cabin.WIDTH, Cabin.HEIGHT);

    for (Floor floor : floors.values()) {
      g.setFont(Font.getFont(Font.SANS_SERIF));
      g.setColor(Color.WHITE);
      g.fillRect(1, (int) floor.getPosition(), Floor.WIDTH, Floor.HEIGHT);
      g.setColor(Color.BLACK);
      g.drawRect(1, (int) floor.getPosition(), Floor.WIDTH, Floor.HEIGHT);
      g.drawString(floor.getNum() + "층", 1, (int) floor.getPosition() + 15);
    }

    renderer.flush();
  }

  private void updateCabin(Cabin cabin, long deltaTime) {
    double motor = motorOutput * (cabin.getVector() == Vector.DOWN ? 1.0 : -1.0);
    double accel = (motor + gravity - forceBreak) / (cabinWeight + (passengerWeight * cabin.getPassengers().size()));
    Vector vector = cabin.getVector();
    Floor target = cabin.getTarget();

    switch(cabin.getState()) {
      case MOVE:
        cabin.setPosition(cabin.getPosition() + (accel * deltaTime * deltaTime)/2.0); // d = (at^2)/2
        if ((vector == Vector.DOWN && cabin.getPosition() > target.getPosition())
            || (vector == Vector.UP && cabin.getPosition() < target.getPosition())) {
          cabin.setPosition(target.getPosition());

          if (cabin.getQueue().isEmpty()) {
            cabin.stop();
          } else {
            cabin.move(cabin.getQueue().peek(), Cabin.State.STOP);
          }
        }
        break;
      case STOP:
        cabin.stop();
        break;
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
}
