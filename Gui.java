import java.awt.event.*;
import javax.swing.*;

class Gui implements ActionListener, KeyListener {

  ///// VARIABLES //////
  private JButton button;
  private int count = 0;
  JTextField textField;

  //// METHODS /////
  public void addTextField(JFrame frame) {
    textField = new JTextField(0);
    textField.addKeyListener(this);
    frame.add(textField);
  }

  public void addButton(JFrame frame) {
    button = new JButton("buh");
    button.addActionListener(this);
    frame.add(button);
    button.setBounds(0, 0, 30, 30);
  }

  public void actionPerformed(ActionEvent e) {
    count++;
    System.out.println("buh" + count);
    button.setIcon(new ImageIcon("buh.jpg"));
    button.setText("");

  }

  public void keyPressed(KeyEvent e) {
    int keyCode = e.getKeyCode();
    System.out.println(keyCode);

  }

  public void keyReleased(KeyEvent e) {

  }

  public void keyTyped(KeyEvent e) {

  }
}