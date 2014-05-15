package fecs.ui;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by jcooky on 2014. 4. 12..
 */
public class Renderer {

  private UserInterface ui;

  private BufferedImage buffer;

  public Renderer(UserInterface ui) {
    this.ui = ui;
    this.buffer = new BufferedImage(ui.getDrawTarget().getWidth(), ui.getDrawTarget().getHeight(), BufferedImage.TYPE_INT_RGB);
  }

  public void flush() {
    buffer.flush();

    ui.getDrawTarget().getGraphics().drawImage(buffer, 0, 0, Color.GRAY, null);
  }

  public Graphics getGraphics() {
    return buffer.getGraphics();
  }
}
