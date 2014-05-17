package fecs.interfaces;

import fecs.model.CabinType;
import fecs.model.FloorType;
import fecs.simulator.Cabin;
import fecs.simulator.Floor;

import java.util.Map;

/**
 * Created by Byoungwoo on 2014-05-16.
 */
public interface IEngine {
  static final int STATE_STOP = 0b00,
    STATE_START = 0b01;

  public Double mass(Cabin cabin);

  Double getGravity();
  void setGravity(Double gravity);

  Double getCabinWeight();
  void setCabinWeight(Double cabinWeight);

  Integer getState();
  void setState(Integer state);

  Double getForceBreak();
  void setForceBreak(Double forceBreak);

  Double getMotorOutput();
  void setMotorOutput(Double motorOutput);

  Integer getCabinLimitPeople();
  void setCabinLimitPeople(Integer cabinLimitPeople);

  Double getCabinLimitWeight();
  void setCabinLimitWeight(Double cabinLimitWeight);

  Double getMoreEnterProbability();
  void setMoreEnterProbability(Double moreEnterProbability);

  Double getPassengerWeight();
  void setPassengerWeight(Double passengerWeight);

  Map<CabinType, Cabin> getCabins();

  Map<FloorType, Floor> getFloors();
}
