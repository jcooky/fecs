package fecs.interfaces;

/**
 * Created by Byoungwoo on 2014-05-16.
 */
public interface IEngine {
  static final int STATE_STOP = 0x00,
    STATE_START = 0x01,
    STATE_FIRE = 0x02,
    STATE_CRASH = 0x04,
    STATE_FLOOD = 0x08,
    STATE_EARTHQUAKE = 0x10,
    STATE_NORMAL = 0x20;

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
}
