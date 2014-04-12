package fecs.ui;


import org.springframework.context.ApplicationContext;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jcooky on 2014. 3. 22..
 */
public class AdminUI extends JFrame implements Runnable {

  private JPanel rootPanel;

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


  private FloorsPanel floorsPanel;
  private CabinPanel cabinPanel;
  private JButton startButt,stopButt,setButt;
  private JTextField gravityText, cabinWeightText, cabinLimitWeightText,motorPowerText;

  private void createUIComponents() {
    this.floorsPanel = applicationContext.getBean(FloorsPanel.class);
    this.cabinPanel = applicationContext.getBean(CabinPanel.class);
  }
  private void ChIdeas_orz(){
    setLayout(new GridLayout(4,2));

    JComponent addArr[] = new JComponent[]{startButt=new JButton("시작"),stopButt=new JButton("정지"),setButt=new JButton("설정"),gravityText,cabinWeightText,cabinLimitWeightText,motorPowerText};
    for(int i=0;i<addArr.length;i++) {
      if (i >= 3) addArr[i] = new JTextField();
      getContentPane().add(addArr[i]);
    }
  }
}
