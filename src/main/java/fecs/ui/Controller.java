package fecs.ui;

import fecs.Circumstance;
import fecs.interfaces.ICircumstance;
import fecs.model.CabinType;
import fecs.model.CircumstanceType;
import fecs.model.FloorType;
import fecs.simulator.Cabin;
import fecs.simulator.Engine;
import fecs.simulator.Floor;
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
    engine.setEngineState(Engine.STATE_START);
  }

  public void stopSimulation() {
    int state = engine.getState() & 1;

    if (state == Engine.STATE_STOP) {
      displayError("already stopped");
      return;
    }
    engine.setEngineState(Engine.STATE_STOP);
  }

  public void triggerFail(String name) {
    int state;

    if (CircumstanceType.FIRE.type().equals(name)) {
      ICircumstance c = Circumstance.get(CircumstanceType.FIRE).setParameter("validate", false);
      String answer = JOptionPane.showInputDialog("which floor? (RANDOM,-1,2~10)");

      Integer val;
      if(answer.equals("RANDOM")) {
        val = (int)(new java.util.Random().nextInt(10));
        if(val==0) val--;
      }else{
        try { val = Integer.parseInt(answer); }
        catch (NumberFormatException e) { displayError("not a number"); return; }
        if (!(val == -1 || (val >= 2 && val <= 10))) { displayError("not in a valid range"); return; }
      }
      Floor fireFloor = engine.getFloors().get(FloorType.valueOf(val));
      c.setParameter("floor", fireFloor);

      c.trigger();

      state = CircumstanceType.FIRE.state();
    } else if (CircumstanceType.CRASH.type().equals(name)) {
      String answer = JOptionPane.showInputDialog("which cabin? (LEFT, RIGHT)");
      if(!answer.equals("LEFT") && !answer.equals("RIGHT")){
        displayError("only 'LEFT' or 'RIGHT' input available");
        return;
      }
      Cabin crashCabin = engine.getCabins().get(CabinType.valueOf(answer));

      Circumstance.get(CircumstanceType.CRASH)
          .setParameter("validate", false)
          .setParameter("cabin", crashCabin)
          .trigger();

      state = CircumstanceType.CRASH.state();
    } else if (CircumstanceType.EARTH_QUAKE.type().equals(name)) {
      Circumstance.get(CircumstanceType.EARTH_QUAKE).setParameter("startTime", System.currentTimeMillis());

      state = CircumstanceType.EARTH_QUAKE.state();
    } else if (CircumstanceType.FLOOD.type().equals(name)) {
      Circumstance.get(CircumstanceType.FLOOD).setParameter("startTime", System.currentTimeMillis());

      state = CircumstanceType.FLOOD.state();
    } else {
      state = CircumstanceType.DEFAULT.state();
    }

    engine.setCircumstanceState(state);
  }

  public void changeGravity(String gravity) {

    try {
      Double val = Double.parseDouble(gravity);

      if (val > 50) val = 50.0;
      else if (val < 1) val = 1.0;

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
        Double cabinWeight = engine.getCabinMass();
        Double passengerWeight = engine.getPassengerMass();

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
        engine.setCabinMass(val);
      } else {
        throw new NumberFormatException();
      }
    } catch (NumberFormatException e) {
      displayError(e.getMessage());
      ui.getCabinMass().setText(engine.getCabinMass().toString());
    }
  }

  public void changePassengerWeight(String weight) {
    try {
      Double val = Double.parseDouble(weight);
      if (val > 0)
        engine.setPassengerMass(val);
      else
        throw new NumberFormatException();
    } catch (NumberFormatException e) {
      displayError(e.getMessage());
      ui.getPassengerMass().setText(engine.getPassengerMass().toString());
    }
  }

  public void changeTotalNumPassengers(String numPassengers) {
    try {
      Integer val = Integer.parseInt(numPassengers);

      if (val > 0) {
        passengerMaker.setMax(val);
        Integer howMany = passengerMaker.getHowMany();

        if (howMany > val) {
          displayError();
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

        if (val > max) {
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
      Double val = Double.parseDouble(moreEnterProbability)/100;

      if (val >= 0 && val <= 1) {
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
        Double passengerWeight = engine.getPassengerMass();
        Double cabinWeight = engine.getCabinMass();
        Integer cabinLimitPeople = engine.getCabinLimitPeople();

        if (val < cabinLimitPeople * passengerWeight + cabinWeight) {
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

  public void displayError() {
    displayError("Error");
  }

  public void displayError(String message) {
    JOptionPane.showMessageDialog(null, StringUtils.defaultIfEmpty(message, "Error"));
  }
}
