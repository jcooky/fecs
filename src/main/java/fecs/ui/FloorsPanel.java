package fecs.ui;

import fecs.FloorSet;
import fecs.model.Floor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jcooky on 2014. 3. 22..
 */
@org.springframework.stereotype.Component
public class FloorsPanel extends JPanel {
  @Autowired
  private FloorSet floorSet;

  public FloorsPanel() {
    setSize(300, 300);
  }

  @Override
  public void paint(Graphics g) {
    for (Floor floor : floorSet) {
      g.setFont(Font.getFont(Font.SANS_SERIF));
      g.setColor(Color.WHITE);
      g.fillRect((int)floor.x+1, (int)floor.y, (int)floor.width, (int)floor.height);
      g.setColor(Color.BLACK);
      g.drawRect((int)floor.x + 1, (int)floor.y, (int)floor.width, (int)floor.height);
      g.drawString(floor.getFloor() + "층", (int)floor.x+1, (int)floor.y+15);
    }

//    super.paint(g);
  }
}
