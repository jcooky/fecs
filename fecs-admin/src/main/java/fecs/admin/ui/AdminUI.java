package fecs.admin.ui;

import javax.swing.*;

/**
 * Created by jcooky on 2014. 3. 22..
 */
public class AdminUI extends JFrame {

  private JPanel rootPanel;

  public AdminUI() {
    super("Failover Elevator Controller Simulator - Administrator");

    setContentPane(rootPanel);

    pack();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    setVisible(true);
  }
}
