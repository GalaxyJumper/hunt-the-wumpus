import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Display extends JFrame {
  ////////////////////////
  // Variables
  /////////////////////////
  private DisplayPanel panel;
  JButton button;
  Gui gui;

  ////////////////////////
  // CONSTRUCTOR
  /////////////////////////

  // Instantiates and initialises a JFrame with set values
  public Display(int width, int height, String name) {

    panel = new DisplayPanel(width, height);

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.addKeyListener(panel);
    this.add(panel);
    this.pack();

    this.setTitle(name);
    this.setVisible(true);
  }

  ////////////////////////
  // METHODS
  /////////////////////////
  public void paint() {
    panel.repaint();
  }

}