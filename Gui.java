import javax.swing.*;
public class Gui extends JPanel{
    public Gui(){
        JFrame frame = new JFrame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(panel);
        frame.add(panel);
        frame.pack();

        frame.setTitle(name);
        frame.setVisible(true);
    }
}
