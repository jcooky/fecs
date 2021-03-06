package fecs.ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import fecs.model.CircumstanceType;
import fecs.simulator.Engine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jcooky on 2014. 3. 22..
 */
@Component("userInterface")
public class UserInterface extends JFrame implements Runnable {

  private JButton startSim;
  private JButton stopSim;
  private JPanel rootPanel;
  private JTextField howMany;
  private JTextField forceBreak;
  private JTextField cabinMass;
  private JTextField totalNumPassengers;
  private JTextField gravity;
  private JTextField motorOutput;
  private JTextField moreEnterProbability;
  private JTextField passengerMass;
  private JTextField cabinLimitPeople;
  private JTextField cabinLimitWeight;
  private JPanel drawTarget;
  private JComboBox planetCombo;
  private JButton earthQuake;
  private JButton fire;
  private JButton flood;
  private JButton crash;

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
    cabinMass.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.changeCabinWeight(cabinMass.getText());
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
    passengerMass.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.changePassengerWeight(passengerMass.getText());
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
      public void actionPerformed(ActionEvent e) {
        controller.changeCabinLimitPeople(cabinLimitPeople.getText());
      }
    });
    if (planetCombo.getItemCount() == 0) {
      planetCombo.addItem("지구");
      planetCombo.addItem("달");
      planetCombo.addItem("화성");
      planetCombo.addItem("금성");
      planetCombo.addItem("직접입력");
    }
    planetCombo.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String selected = planetCombo.getSelectedItem().toString();
        if (selected.equals("직접입력")) {
          gravity.setEnabled(true);
          gravity.requestFocus();
        } else {
          gravity.setEnabled(false);
          gravity.setText(Engine.gravityTable.get(selected).toString());
          gravity.getActionListeners()[0].actionPerformed(null);
        }
      }
    });
    earthQuake.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.triggerFail(CircumstanceType.EARTH_QUAKE.type());

        startFail();
      }
    });
    fire.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.triggerFail(CircumstanceType.FIRE.type());

        startFail();
      }
    });
    flood.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.triggerFail(CircumstanceType.FLOOD.type());

        startFail();
      }
    });
    crash.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.triggerFail(CircumstanceType.CRASH.type());

        startFail();
      }
    });
  }

  public void startFail() {
    earthQuake.setEnabled(false);
    fire.setEnabled(false);
    flood.setEnabled(false);
    crash.setEnabled(false);
  }

  public void endFail() {
    earthQuake.setEnabled(true);
    fire.setEnabled(true);
    flood.setEnabled(true);
    crash.setEnabled(true);
  }

  public Renderer getRenderer() {
    return renderer;
  }

  public JTextField getHowMany() {
    return howMany;
  }

  public JTextField getForceBreak() {
    return forceBreak;
  }

  public JTextField getCabinMass() {
    return cabinMass;
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

  public JTextField getPassengerMass() {
    return passengerMass;
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

  {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
    $$$setupUI$$$();
  }

  /**
   * Method generated by IntelliJ IDEA GUI Designer
   * >>> IMPORTANT!! <<<
   * DO NOT edit this method OR call it in your code!
   *
   * @noinspection ALL
   */
  private void $$$setupUI$$$() {
    rootPanel = new JPanel();
    rootPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
    final JPanel panel1 = new JPanel();
    panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    rootPanel.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    drawTarget = new JPanel();
    drawTarget.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    drawTarget.setBackground(Color.gray);
    panel1.add(drawTarget, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(300, 555), null, null, 0, false));
    final JPanel panel2 = new JPanel();
    panel2.setLayout(new GridLayoutManager(4, 4, new Insets(0, 0, 0, 0), -1, -1));
    rootPanel.add(panel2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    earthQuake = new JButton();
    earthQuake.setText("지진");
    panel2.add(earthQuake, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
    final JPanel panel3 = new JPanel();
    panel3.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
    panel3.setEnabled(false);
    panel2.add(panel3, new GridConstraints(0, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, true));
    startSim = new JButton();
    startSim.setText("시뮬레이션 시작");
    panel3.add(startSim, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    stopSim = new JButton();
    stopSim.setText("시뮬레이션 정지");
    panel3.add(stopSim, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JPanel panel4 = new JPanel();
    panel4.setLayout(new GridLayoutManager(5, 4, new Insets(0, 0, 0, 0), -1, -1, true, true));
    panel2.add(panel4, new GridConstraints(1, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(610, 509), null, 0, false));
    final JLabel label1 = new JLabel();
    label1.setText("초당 승객 생성(명)");
    panel4.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    howMany = new JTextField();
    howMany.setText("1");
    panel4.add(howMany, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JLabel label2 = new JLabel();
    label2.setText("브레이크 강도(Nm/s)");
    panel4.add(label2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    forceBreak = new JTextField();
    forceBreak.setText("10000");
    panel4.add(forceBreak, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JLabel label3 = new JLabel();
    label3.setText("캐빈 질량(kg)");
    panel4.add(label3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    cabinMass = new JTextField();
    cabinMass.setText("700");
    panel4.add(cabinMass, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JLabel label4 = new JLabel();
    label4.setText("전체 승객 수(명)");
    panel4.add(label4, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    totalNumPassengers = new JTextField();
    totalNumPassengers.setText("30");
    panel4.add(totalNumPassengers, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JLabel label5 = new JLabel();
    label5.setText("중력(m/s²)");
    panel4.add(label5, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JPanel panel5 = new JPanel();
    panel5.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
    panel4.add(panel5, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    planetCombo = new JComboBox();
    planetCombo.setLightWeightPopupEnabled(false);
    panel5.add(planetCombo, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    gravity = new JTextField();
    gravity.setEnabled(false);
    gravity.setText("9.80665");
    panel5.add(gravity, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    final JLabel label6 = new JLabel();
    label6.setText("모터 출력(Nm/s)");
    panel4.add(label6, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    motorOutput = new JTextField();
    motorOutput.setText("27000");
    panel4.add(motorOutput, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JLabel label7 = new JLabel();
    label7.setText("정원초과시 더 탈 확률(%)");
    panel4.add(label7, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    moreEnterProbability = new JTextField();
    moreEnterProbability.setText("22");
    panel4.add(moreEnterProbability, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JLabel label8 = new JLabel();
    label8.setText("승객 질량(kg)");
    panel4.add(label8, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    passengerMass = new JTextField();
    passengerMass.setText("80");
    panel4.add(passengerMass, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JLabel label9 = new JLabel();
    label9.setText("캐빈 정원(명)");
    panel4.add(label9, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    cabinLimitPeople = new JTextField();
    cabinLimitPeople.setText("12");
    panel4.add(cabinLimitPeople, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JLabel label10 = new JLabel();
    label10.setText("캐빈 한계 질량(kg)");
    panel4.add(label10, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    cabinLimitWeight = new JTextField();
    cabinLimitWeight.setText("1660");
    panel4.add(cabinLimitWeight, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    fire = new JButton();
    fire.setText("화재");
    panel2.add(fire, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    flood = new JButton();
    flood.setText("수해");
    panel2.add(flood, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    crash = new JButton();
    crash.setText("추락");
    panel2.add(crash, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JLabel label11 = new JLabel();
    label11.setText("장애 상황 수동 발생");
    panel2.add(label11, new GridConstraints(2, 0, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
  }

  /**
   * @noinspection ALL
   */
  public JComponent $$$getRootComponent$$$() {
    return rootPanel;
  }
}