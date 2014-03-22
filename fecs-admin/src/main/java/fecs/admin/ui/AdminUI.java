package fecs.admin.ui;

import org.springframework.context.ApplicationContext;

import javax.swing.*;

/**
 * Created by jcooky on 2014. 3. 22..
 */
public class AdminUI extends JFrame implements Runnable {

  private JPanel rootPanel;

  private FloorsPanel floorsPanel;
  private CavinPanel cavinPanel;

  private ApplicationContext applicationContext;

  public AdminUI(ApplicationContext applicationContext) {
    super("Failover Elevator Controller Simulator - Administrator");

    this.applicationContext = applicationContext;
  }

  public void run() {
    setContentPane(rootPanel);

    pack();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    setVisible(true);
  }

  private void createUIComponents() {
    this.floorsPanel = applicationContext.getBean(FloorsPanel.class);
    this.cavinPanel = applicationContext.getBean(CavinPanel.class);
  }
}
