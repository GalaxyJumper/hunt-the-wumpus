import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class DisplayPanel extends JPanel implements ActionListener, KeyListener {
  ///// VARIABLES /////
  Image image;
  private double x = 90;
  private double y = 90;
  private double xVel = 0;
  private double yVel = 0;
  private boolean isFirstFrame = true;
  private int xState = 0;
  private int yState = 0;
  Timer timer;
  Graphics2D g2d;

  ////////////////////////
  // CONSTRUCTOR
  ///////////////////////

  // Creates a Panel to put within the Frame - dimensions are assigned here so
  // that the top bar does not get in the way of the actual drawing spaaaaaace

  public DisplayPanel(int width, int height) {
    // setup
    image = new ImageIcon("wumpus.png").getImage();

    this.setPreferredSize(new Dimension(width, height));
    // TIMER - the game tick of the program. Sends an event out every 17 or 33
    // milliseconds to be picked up by the actionPerformed method.
    // 17 ms = ~60 fps
    // 33 ms = ~30 fps
    int fps = 30;
    timer = new Timer(1000 / fps, this);
    timer.start();
  }

  ////////////////////////
  // METHODS
  /////////////////////////

  // Draws things to the JFrame; called behind the scenes when a new JFrame is
  // instantiated
  // IMPORTANT: Stuff gets drawn here
  public void paint(Graphics g) {

    super.paint(g);
    g2d = (Graphics2D) g;

    g2d.fillOval((int) x, (int) y, 40, 40);

  }

  // The physics/main loop - every time the Timer sends an event this method is
  // called
  public void actionPerformed(ActionEvent e) {

    if (x > this.getWidth() - 40 || x < 0) {
      xVel = -xVel * 0.7;
      x = (x < 0) ? 0 : this.getWidth() - 40;

    }
    if (y > this.getHeight() - 40 || y < 0) {
      yVel = -yVel * 0.2;
      y = (y < 0) ? 0 : this.getHeight() - 40;
    }
    x += xVel;
    y += yVel;

    repaint();
  }

  public void keyPressed(KeyEvent e) {

    int keyCode = e.getKeyCode();

    // xState 1 = left 2 = right 0 = nothing
    // yState 1 = up 2 = down 0 = nothing
    if (keyCode == KeyEvent.VK_UP) {
      yState = 1;
    }
    if (keyCode == KeyEvent.VK_DOWN) {
      yState = 2;
    }
    if (keyCode == KeyEvent.VK_LEFT) {
      xState = 1;
    }
    if (keyCode == KeyEvent.VK_RIGHT) {
      xState = 2;
    }

    if (xState == 1) {
      xVel += -4;
    } else if (xState == 2) {
      xVel += 4;
    }

    if (yState == 1) {
      yVel += -4;
    } else if (yState == 2) {
      yVel += 4;
    }
  }

  public void keyReleased(KeyEvent e) {

    int keyCode = e.getKeyCode();

    if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN) {
      yState = 0;
    }
    if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
      xState = 0;
    }
  }

  public void keyTyped(KeyEvent e) {

  }

}