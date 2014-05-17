package fecs.ui;

import fecs.Circumstance;
import fecs.interfaces.ICircumstance;
import fecs.model.CabinType;
import fecs.simulator.Cabin;
import fecs.simulator.Engine;
import fecs.simulator.PassengerMaker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

/**
 * Created by jcooky on 2014. 5. 11..
 */
@Component
public class Controller {

  @Autowired
  private Engine engine;

  @Autowired
  private PassengerMaker passengerMaker;

  @Autowired
  private UserInterface ui;

  public void startSimulation() {
    int state = engine.getState() & 1;

    if (Engine.STATE_START == state) {
      displayError("already started");
      return;
    }

    engine.setState(engine.getState() | Engine.STATE_START);
  }

  public void stopSimulation() {
    int state = engine.getState() & 1;

    if (state == Engine.STATE_STOP) {
      displayError("already stopped");
      return;
    }
    engine.setState(engine.getState() & Engine.STATE_STOP);
  }

  public void triggerFail(String name) {
    int state;

    if (ICircumstance.FIRE.equals(name)) {
      state = ICircumstance.STATE_FIRE;
    } else if (ICircumstance.CRASH.equals(name)) {
      String answer = JOptionPane.showInputDialog("what cabin? (LEFT, RIGHT)");
      Cabin crashCabin = engine.getCabins().get(CabinType.valueOf(answer));

      Circumstance.get(ICircumstance.CRASH).setParameter("cabin", crashCabin).trigger();

      state = ICircumstance.STATE_CRASH;
    } else if (ICircumstance.EARTH_QUAKE.equals(name)) {
      state = ICircumstance.STATE_EARTH_QUAKE;
    } else if (ICircumstance.FLOOD.equals(name)) {
      state = ICircumstance.STATE_FLOOD;
    } else {
      state = ICircumstance.STATE_DEFAULT;
    }

    engine.setState((state << 1) | (engine.getState() & 0x00000001));
  }

  public void changeGravity(String gravity) {

    try {
      Double val = Double.parseDouble(gravity);

      if (val > 50) val = 50.0;
      else if (val < 0) val = 0.0;
      else val = Engine.earthGravity;

      engine.setGravity(val);
      ui.getGravity().setText(String.valueOf(val));

    } catch (NumberFormatException e) {
      displayError(e.getMessage());
      ui.getGravity().setText(engine.getGravity().toString());
    }
  }

  public void changeCabinLimitPeople(String limit) {
    try {
      Integer val = Integer.parseInt(limit);

      if (val > 0) {
        Double cabinLimitWeight = engine.getCabinLimitWeight();
        Double cabinWeight = engine.getCabinWeight();
        Double passengerWeight = engine.getPassengerWeight();

        if (cabinWeight + val * passengerWeight > cabinLimitWeight) {
          val = (int) (Math.floor((cabinLimitWeight - cabinWeight) / passengerWeight));
          ui.getCabinLimitPeople().setText(val.toString());
        }
        engine.setCabinLimitPeople(val);
      } else {
        throw new NumberFormatException();
      }
    } catch (NumberFormatException e) {
      displayError(e.getMessage());
      ui.getCabinLimitPeople().setText(engine.getCabinLimitPeople().toString());
    }
  }

  public void changeCabinWeight(String weight) {
    try {
      Double val = Double.parseDouble(weight);

      if (val > 0) {
        engine.setCabinWeight(val);
      } else {
        throw new NumberFormatException();
      }
    } catch (NumberFormatException e) {
      displayError(e.getMessage());
      ui.getCabinWeight().setText(engine.getCabinWeight().toString());
    }
  }

  public void changePassengerWeight(String weight) {
    try {
      Double val = Double.parseDouble(weight);
      if (val > 0)
        engine.setPassengerWeight(val);
      else
        throw new NumberFormatException();
    } catch (NumberFormatException e) {
      displayError(e.getMessage());
      ui.getPassengerWeight().setText(engine.getPassengerWeight().toString());
    }
  }

  public void changeTotalNumPassengers(String numPassengers) {
    try {
      Integer val = Integer.parseInt(numPassengers);

      if (val > 0) {
        passengerMaker.setMax(val);
        Integer howMany = passengerMaker.getHowMany();

        if (howMany >= val) {
          changePassengerCreated(numPassengers);
        }
      } else
        throw new NumberFormatException();
    } catch (NumberFormatException e) {
      displayError(e.getMessage());
      ui.getTotalNumPassengers().setText(passengerMaker.getHowMany().toString());
    }
  }

  public void changePassengerCreated(String numCreatedPerSec) {
    try {
      Integer val = Integer.parseInt(numCreatedPerSec);

      if (val > 0) {
        passengerMaker.setHowMany(val);
        Integer max = passengerMaker.getMax();

        if (val >= max) {
          displayError();
          changeTotalNumPassengers(numCreatedPerSec);
        }
      } else
        throw new NumberFormatException();
    } catch (NumberFormatException e) {
      displayError(e.getMessage());
      ui.getHowMany().setText(passengerMaker.getHowMany().toString());
    }
  }

  public void changeMoreEnterProbability(String moreEnterProbability) {
    try {
      Double val = Double.parseDouble(moreEnterProbability);

      if (val > 0) {
        engine.setMoreEnterProbability(val);
      } else {
        throw new NumberFormatException();
      }

    } catch (NumberFormatException e) {
      displayError(e.getMessage());
      ui.getMoreEnterProbability().setText(engine.getMoreEnterProbability().toString());
    }
  }

  public void changeMotorOutput(String motorOutput) {
    try {
      Double val = Double.parseDouble(motorOutput);

      if (val > 0) {
        engine.setMotorOutput(val);
      } else {
        throw new NumberFormatException();
      }

    } catch (NumberFormatException e) {
      displayError(e.getMessage());
      ui.getMotorOutput().setText(engine.getMotorOutput().toString());
    }

  }

  public void changeForceBreak(String forceBreak) {
    try {
      Double val = Double.parseDouble(forceBreak);

      if (val > 0) {
        engine.setForceBreak(val);

      } else {
        throw new NumberFormatException();
      }

    } catch (NumberFormatException e) {
      displayError(e.getMessage());
      ui.getForceBreak().setText(engine.getForceBreak().toString());
    }
  }

  public void changeCabinLimitWeight(String cabinLimitWeight) {
    try {
      Double val = Double.parseDouble(cabinLimitWeight);

      if (val > 0) {
        Double passengerWeight = engine.getPassengerWeight();
        Double cabinWeight = engine.getCabinWeight();
        Integer cabinLimitPeople = engine.getCabinLimitPeople();

        if (val > cabinLimitPeople * passengerWeight + cabinWeight) {
          val = cabinLimitPeople * passengerWeight + cabinWeight;
          ui.getCabinLimitWeight().setText(val.toString());
        }

        engine.setCabinLimitWeight(val);

      } else {
        throw new NumberFormatException();
      }

    } catch (NumberFormatException e) {
      displayError(e.getMessage());
      ui.getHowMany().setText(passengerMaker.getHowMany().toString());
    }
  }

  private void displayError() {
    displayError("Error");
  }

  private void displayError(String message) {
    JOptionPane.showMessageDialog(null, StringUtils.defaultIfEmpty(message, "Error"));
  }
}
