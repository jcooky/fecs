package fecs.ui;

import org.springframework.context.ApplicationContext;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by jcooky on 2014. 3. 22..
 */
public class AdminUI extends JFrame implements Runnable {

  private JPanel rootPanel,controlPanel;

  private ApplicationContext applicationContext;
  public AdminUI(ApplicationContext applicationContext) {
    super("Failover Elevator Controller Simulator - Administrator");
    this.applicationContext = applicationContext;
  }
  public void run() {
    setContentPane(rootPanel);
    ChIdeas_orz();
    pack();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    setVisible(true);
  }

  private FloorsPanel floorsPanel;
  private CabinPanel cabinPanel;
  private JButton startButt,stopButt,setButt;
  private JTextField gravityText, cabinWeightText, cabinLimitWeightText,motorPowerText,passengerWeightText;

  private void createUIComponents() {
    this.floorsPanel = applicationContext.getBean(FloorsPanel.class);
    this.cabinPanel = applicationContext.getBean(CabinPanel.class);
  }
  private void ChIdeas_orz(){
    rootPanel.setLayout(new BoxLayout(rootPanel,BoxLayout.Y_AXIS));
    controlPanel=new JPanel(new GridBagLayout());
    GridBagConstraints bag=new GridBagConstraints();
    bag.fill=GridBagConstraints.HORIZONTAL;
    bag.gridx=0; bag.gridy=0;
    bag.weightx=1;
    bag.gridwidth=1;
    JComponent addArr[] = new JComponent[]{startButt=new JButton("시작"),stopButt=new JButton("정지"),setButt=new JButton("설정")};
    for(JComponent toAdd : addArr) {
      controlPanel.add(toAdd, bag);
      bag.gridx++;
    }
    addArr=new JComponent[]{gravityText,cabinWeightText,passengerWeightText,cabinLimitWeightText,motorPowerText};
    String namesArr[] = {"중력(N)","캐빈무게(Kg)","승객무게(Kg)","캐빈한계중량(Kg)","모터출력(N)"};
    int i=0;
    for(JComponent toAdd : addArr){
      bag.gridx=0; bag.gridy++; bag.gridwidth=1;
      controlPanel.add(new JLabel(namesArr[i++]),bag);
      bag.gridx++; bag.gridwidth=2;
      controlPanel.add(toAdd = new JTextField(), bag);
    }

    rootPanel.add(controlPanel);
  }
}