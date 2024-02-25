import javax.swing.*;
import java.awt.*;

public class Gui extends JPanel{
    /////////////////////////////////////
    // CONSTRUCTOR(S)
    /////////////////////////////////////
    public Gui(String name, int width, int height){
        // Create a new Frame for everything to live in
        JFrame frame = new JFrame();
        // Debug message
        System.out.println("New display instantiated with dimensions " + width + "x" + height);
        // Set display size
        this.setPreferredSize(new Dimension(width, height));
        // Set default close operation (end program once the window is closed)
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // for later 
        // frame.addKeyListener();

        //Put this panel into its frame so it can be displayed
        frame.add(this);
        //Make the screen fit around the panel so that there is no overlap
        frame.pack();
        //Make the window visible and set its name to the given name
        frame.setTitle(name);
        frame.setVisible(true);
    }
    
    public Gui(String name){
        JFrame frame = new JFrame();
        System.out.println("New display instantiated with default dimensions 960x540");
        this.setPreferredSize(new Dimension(960, 540));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // for later frame.addKeyListener();
        frame.add(this);
        frame.pack();
        frame.setTitle(name);
        frame.setVisible(true);
    }
    /////////////////////////////////////
    // METHODS
    /////////////////////////////////////
}
