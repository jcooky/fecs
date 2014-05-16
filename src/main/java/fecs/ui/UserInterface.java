package fecs.ui;

import fecs.simulator.Engine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jcooky on 2014. 3. 22..
 */
@Component
public class UserInterface extends JFrame implements Runnable {

  private JButton startSim;
  private JButton stopSim;
  private JButton 화재Button;
  private JPanel rootPanel;
  private JTextField howMany;
  private JTextField forceBreak;
  private JTextField cabinWeight;
  private JTextField totalNumPassengers;
  private JTextField gravity;
  private JTextField motorOutput;
  private JTextField moreEnterProbability;
  private JTextField passengerWeight;
  private JTextField cabinLimitPeople;
  private JTextField cabinLimitWeight;
  private JPanel drawTarget;
  private JComboBox planetCombo;
  
  @Autowired
  private Controller controller;

  @Autowired
  private Engine engine;

  private Renderer renderer;

  public UserInterface() {
    super("Failover Elevator Controller Simulator - Administrator");
  }

  public void run() {
    setContentPane(rootPanel);
    pack();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    setVisible(true);
    init();
    SwingUtilities.invokeLater(engine);
  }

  private void init() {
    renderer = new Renderer(this);

    startSim.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.startSimulation();
      }
    });
    stopSim.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.stopSimulation();
      }
    });
    howMany.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.changePassengerCreated(howMany.getText());
      }
    });
    forceBreak.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.changeForceBreak(forceBreak.getText());
      }
    });
    cabinWeight.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.changeCabinWeight(cabinWeight.getText());
      }
    });
    totalNumPassengers.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.changeTotalNumPassengers(totalNumPassengers.getText());
      }
    });
    gravity.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.changeGravity(gravity.getText());
      }
    });
    motorOutput.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.changeMotorOutput(motorOutput.getText());
      }
    });
    moreEnterProbability.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.changeMoreEnterProbability(moreEnterProbability.getText());
      }
    });
    passengerWeight.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.changePassengerWeight(passengerWeight.getText());
      }
    });
    cabinLimitWeight.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.changeCabinLimitWeight(cabinLimitWeight.getText());
      }
    });
    cabinLimitPeople.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) { controller.changeCabinLimitPeople(cabinLimitPeople.getText()); }
    });
    planetCombo.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (planetCombo.getSelectedItem().toString().equals("직접입력")) {
          gravity.setEnabled(true);
          gravity.requestFocus();
        } else {
          gravity.setEnabled(false);
          gravity.setText(Engine.gravityTable[planetCombo.getSelectedIndex()].toString());
        }
      }
    });
  }

  public Renderer getRenderer() { return renderer; }

  public JTextField getHowMany() {
    return howMany;
  }

  public JTextField getForceBreak() {
    return forceBreak;
  }

  public JTextField getCabinWeight() {
    return cabinWeight;
  }

  public JTextField getTotalNumPassengers() {
    return totalNumPassengers;
  }

  public JTextField getGravity() {
    return gravity;
  }

  public JTextField getMotorOutput() {
    return motorOutput;
  }

  public JTextField getMoreEnterProbability() {
    return moreEnterProbability;
  }

  public JTextField getPassengerWeight() {
    return passengerWeight;
  }

  public JTextField getCabinLimitPeople() {
    return cabinLimitPeople;
  }

  public JTextField getCabinLimitWeight() {
    return cabinLimitWeight;
  }

  public JPanel getDrawTarget() {
    return drawTarget;
  }
}