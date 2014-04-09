package fecs.ui;

import fecs.CabinsController;
import fecs.model.Cabin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jcooky on 2014. 3. 22..
 */
@Component
public class CabinPanel extends JPanel {
  @Autowired
  private CabinsController cabinsController;

  @Override
  public void paint(Graphics g) {
    for (Cabin cabin : cabinsController.getBoth()) {
      cabin.update();

      g.setColor(Color.WHITE);
      g.fillRect((int)cabin.x+1, (int)cabin.y, (int)cabin.width, (int)cabin.height);
      g.setColor(Color.BLACK);
      g.drawRect((int)cabin.x+1, (int)cabin.y, (int)cabin.width, (int)cabin.height);
    }
//    super.paint(g);
  }
}
