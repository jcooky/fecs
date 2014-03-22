package fecs.admin.ui;

import fecs.admin.FloorSet;
import fecs.admin.model.Floor;
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
      floor.draw(g);
    }

//    super.paint(g);
  }
}
