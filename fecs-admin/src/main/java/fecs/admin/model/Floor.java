package fecs.admin.model;

import fecs.admin.Drawable;

import java.awt.*;

/**
 * Created by jcooky on 2014. 3. 22..
 */
public class Floor extends Rectangle implements Drawable {
  private int floor;

  public Floor(int floor, Rectangle rectangle) {
    super(rectangle);

    this.floor = floor;
  }

  public int getFloor() {
    return floor;
  }

  public boolean containsHeight(int y, int height) {
    return this.y <= y && this.y + this.height >= y + height;
  }

  public void draw(Graphics g) {
    g.setFont(Font.getFont(Font.SANS_SERIF));
    g.setColor(Color.WHITE);
    g.fillRect(this.x+1, this.y, this.width, this.height);
    g.setColor(Color.BLACK);
    g.drawRect(this.x+1, this.y, this.width, this.height);
    g.drawString(this.floor + "층", this.x+1, this.y+15);
  }
}
