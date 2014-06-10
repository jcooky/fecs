package fecs.simulator;

import fecs.Circumstance;
import fecs.Fecs;
import fecs.model.CabinType;
import fecs.model.CircumstanceType;
import fecs.model.FloorType;
import fecs.ui.Renderer;
import fecs.ui.UserInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by jcooky on 2014. 5. 12..
 */
@Component("engine")
public class Engine implements Runnable, InitializingBean {
  static public final int STATE_STOP = 0b00,
      STATE_START = 0b01;
  static public final double CABIN_MOVE_THRESHOLD=10*0.01; //30cm in m
  static public final double RealToPixelRatio = Floor.PIXEL_HEIGHT / Floor.REAL_HEIGHT;

  @Autowired
  private UserInterface ui;

  private final Logger logger = LoggerFactory.getLogger(Engine.class);

  private Long lastUpdateTime = System.currentTimeMillis();

  /* gravity related constants */
  public static final Double earthGravity = 9.80665d;
//  public static final Double[] gravityTable = new Double[]{earthGravity, 1.622d, 3.711d, 8.87d}; // 지구, 달, 화성, 금성
  public static final Map<String,Double> gravityTable=new HashMap<String,Double>(){{
    this.put("지구",earthGravity);
    this.put("달",1.622d);
    this.put("화성",3.711d);
    this.put("금성",8.87d);
  }};

  private Double gravity = earthGravity;
  private Map<CabinType, Cabin> cabins = new EnumMap<>(CabinType.class);
  private Map<FloorType, Floor> floors = new EnumMap<>(FloorType.class);
  private Double cabinMass = 700d;
  private Integer state = CircumstanceType.DEFAULT.state() <<1;
  private Double forceBreak = 10000d;
  private Double motorOutput = 27000d;
  private Integer cabinLimitPeople = 12;
  private Double cabinLimitWeight = 1660d; // = cabinLimitPeople*passengerMass+cabinMass;
  private Double moreEnterProbability = 0.22;
  private Double passengerMass = 80d;
  private Set<Floor> pushedFloorSet = new HashSet<>(FloorType.values().length);
  @Override
  public void run() {

    try {
      if (Fecs.getApplicationContext() == null)
        return;

      Long currentTime = System.currentTimeMillis();
      double deltaTime = (currentTime - lastUpdateTime) * 0.001;

        if (getEngineState() == STATE_START) { //last bit is 1 = started
          int s = getCircumstanceState()-1; //0 is null state(error)
          if (s >= CircumstanceType.values().length || s <0) throw new Exception("unstable state value with "+ String.valueOf(s));
          Circumstance.get(CircumstanceType.values()[s])
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
      try {
        Thread.sleep(1);
      } catch (InterruptedException e) {
        logger.error(e.getMessage(), e);
      }
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
    int fsize = g.getFont().getSize();
    JPanel target = ui.getDrawTarget();

      g.setColor(Color.GRAY);
      g.fillRect(0, 0, target.getWidth(), target.getHeight());

      Map<CabinType, Integer> xmap = new HashMap<CabinType, Integer>() {{
        this.put(CabinType.LEFT, Floor.PIXEL_WIDTH + 10);
        this.put(CabinType.RIGHT, Floor.PIXEL_WIDTH + 10 + Cabin.PIXEL_WIDTH + 30);
      }};

      for (CabinType type : cabins.keySet()) {
        Integer x = xmap.get(type);
        Cabin cabin = cabins.get(type);

        int passengers = cabin.getPassengers().size();
        double cabinY=cabin.getPosition()*RealToPixelRatio,
          cabinH=(double)cabin.PIXEL_HEIGHT,
          cabinW=(double)cabin.PIXEL_WIDTH;

        g.setColor(Color.WHITE);
        g.fillRect(x, (int) cabinY, (int)cabinW, (int)cabinH);
        g.setColor(Color.BLACK);
        g.drawRect(x, (int) cabinY, (int)cabinW, (int)cabinH);
        //cabin fullness
        g.drawString(passengers>=cabinLimitPeople?passengers>cabinLimitPeople?"초과":"만원":"", (int) (x + cabinW / 2.0 - fsize), (int) (cabinY + cabinH / 2.0 - fsize));
        //passengers on cabin
        g.drawString(String.format("%d명",passengers), (int) (x + cabinW / 2.0 - fsize * 2.0 / 2.0), (int) (cabinY + cabinH / 2.0));
        //cabin weight
        g.drawString(String.format("%.0fkg",mass(cabin)), x, (int) (cabinY + (cabinH / 2.0) + fsize));
        //cabin speed
        g.drawString(String.format("%.1fm/s",Math.abs(cabin.getVelocity())), x, (int) (cabinY + cabinH / 2.0 + fsize * 2.0));
      }

      for (Floor floor : floors.values()) {
        int passengers = floor.getPassengers().size();
        double floorY = floor.getPosition()*RealToPixelRatio;
        g.setFont(Font.getFont(Font.SANS_SERIF));
        g.setColor(Color.WHITE);
        g.fillRect(1, (int) floorY, Floor.PIXEL_WIDTH, Floor.PIXEL_HEIGHT);
        g.setColor(Color.BLACK);
        g.drawRect(1, (int) floorY, Floor.PIXEL_WIDTH, Floor.PIXEL_HEIGHT);
        g.drawString(floor.getNum() + "층", 1, (int) floorY + 15);
        g.drawString(Integer.toString(passengers), (int) (1 + (double) (Floor.PIXEL_WIDTH) / 2.0 - (double) fsize / 2.0),
            (int) (floorY + (double) Floor.PIXEL_HEIGHT / 2.0));
      }
      if((state>>1) == CircumstanceType.FIRE.state()) {
        Floor firedFloor = (Floor)Circumstance.get(CircumstanceType.FIRE).getParameter("floor");
        if(null!=firedFloor)
          g.drawString("화재", Floor.PIXEL_WIDTH / 2 - fsize, (int)(firedFloor.getPosition() * RealToPixelRatio +((double) Floor.PIXEL_HEIGHT/2.0 + fsize * 2.0)));
      }
      renderer.flush();
  }

  public void updateCabin(Cabin cabin, double accel, double deltaTime) {
    if(deltaTime>0.2)return;
    double vi=cabin.getVelocity(),
      vf=vi+accel*deltaTime; //vf=vi+ a*dt
    if(Math.abs(vf)>20) vf = 0d;
    double ds=0.5*deltaTime*(vf+vi);
    cabin.setPosition(cabin.getPosition() + ds);//sf=si+integral(dv=vf-vi)=si+dt*(vf-vi)=si+dt*(a*dt)=si+a*dt^2
    cabin.setVelocity(vf);

    double min = floors.get(FloorType.TENTH).getPosition(),
        max = floors.get(FloorType.UNDER_FIRST).getPosition();
    if(cabin.getPosition()>max){ cabin.setPosition(max);}
    if(cabin.getPosition()<min){ cabin.setPosition(min);}
  }

  public void updateCabin(Cabin cabin, double deltaTime) {
    if(deltaTime>0.2)return;
    if (cabin.isOn()) {
      switch (cabin.getState()) {
        case MOVE:
          double vector = cabin.getVector();
          int vectorUnit = (int) (vector/Math.abs(vector));
          double motor = motorOutput*earthGravity*deltaTime; // Nm/s (weight) -> kgfm
          if(motor<=gravity){
            logger.debug("motor is too weak or gravity is too strong.");
            motor=gravity;
          }
          Floor target = cabin.getTarget();
          double leftVector =target.getPosition()-cabin.getPosition();
          int leftVectorUnit = (int)(leftVector/Math.abs(leftVector));
          double accel = motor/mass(cabin) * leftVectorUnit;
          if(Math.abs(cabin.getVelocity())>1)
            accel *= Math.abs(vector*0.5) < Math.abs(leftVector) ? 1 : -1; //brake on half point
          if (Double.isNaN(accel)) {
            accel = vectorUnit;
          }
//          logger.debug(String.format("%f -> %f (%f) : %f",cabin.getPosition(), target.getPosition(),vector,accel));
//          logger.debug(String.format("%s->%d, %f, %f, %f",cabin.getName(),target.getNum(),accel,leftVector,vector*0.5));
          updateCabin(cabin, accel, deltaTime);

          if (Math.abs(leftVector)<CABIN_MOVE_THRESHOLD) { //arrive
            cabin.setPosition(target.getPosition());
            cabin.stop();
//            cabin.getQueue().remove(target);
            logger.debug(String.format("%s->%d done",cabin.getName(),target.getNum()));
          }
          break;
        case STOP:
          logger.info("cabin({}) arrived", cabin.getName());

          for (Floor floor : floors.values()) {
            if (cabin.getPosition() == floor.getPosition()) {
              logger.debug("on {}", floor);
              Iterator<Passenger> it = cabin.getPassengers().iterator();
              while (it.hasNext()) {
                Passenger p = it.next();
                if (p.getDest().equals(floor.getNum())) {
                  p.setState(Passenger.State.NO_WAIT);
                  it.remove();
                  floor.getPassengers().add(p);
                }
              }
              cabin.getQueue().remove(floor);
            }
          }

          if (cabin.getQueue().size()>0) cabin.move(); // to next queued floor
          break;
      }
    }
  }


  private void initCabins() {
    for(CabinType t : CabinType.values()){
      Cabin cabin = new Cabin(t.toString());
      cabin.setPosition(floors.get(FloorType.TENTH).getPosition());
      cabins.put(t, cabin);
    }
  }

  private void initFloors() throws Exception {
    double y = 0;
    int numGroundFloors = 10;

    for (int i = numGroundFloors; i >= -1; --i) {
      if (i != 0) {
        Floor floor = new Floor(i);
        floor.setPosition(y);

        floors.put(FloorType.valueOf(i), floor);

        y += Floor.REAL_HEIGHT;
      }
    }
  }

  public Double mass(Cabin cabin) {
    return cabinMass + passengerMass * cabin.getPassengers().size();
  }

  public Double getGravity() {
    return gravity;
  }

  public void setGravity(Double gravity) {
    this.gravity = gravity;
  }

  public Double getCabinMass() {
    return cabinMass;
  }

  public void setCabinMass(Double cabinMass) {
    this.cabinMass = cabinMass;
  }
  public void setState(Integer state){
    this.state=state;
  }
  public void setEngineState(Integer state) {
    this.state = (getCircumstanceState() << 1) | getEngineState(state);
  }

  public void setCircumstanceState(Integer state){
    this.state = (state<<1) | getEngineState();
  }

  public Integer getState() {
    return state;
  }

  public Integer getEngineState(Integer s){return s & 1;}

  public Integer getEngineState(){ return getEngineState(state);}

  public Integer getCircumstanceState(Integer s){return (s & ~1) >>1;}

  public Integer getCircumstanceState(){return getCircumstanceState(state);}

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

  public Double getPassengerMass() {
    return passengerMass;
  }

  public void setPassengerMass(Double passengerMass) {
    this.passengerMass = passengerMass;
  }

  public Map<FloorType, Floor> getFloors() {
    return floors;
  }

  public Map<CabinType, Cabin> getCabins() {
    return cabins;
  }

  public Set<Floor> getPushedFloorSet() {
    return pushedFloorSet;
  }
}
