import javax.swing.*;
import java.awt.*;

public class Gui extends JPanel{
    /////////////////////////////////////
    // CONSTRUCTOR(S)
    /////////////////////////////////////
    Graphics2D g2d;
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

    public void paint(Graphics g){
        g2d = (Graphics2D)g;
        drawHex(100, 100, 100);
    }
    public void drawHex(int centerX, int centerY, int radius){
        int[] currentPos = new int[2];
        int[] lastPos = {radius, 0};
        for(int i = 0; i < 6; i++) {
            currentPos[0] = (int)(centerX + radius * Math.cos((Math.PI/6) * i));
            currentPos[1] = (int)(centerY + radius * Math.sin((Math.PI/6) * i));
            g2d.drawLine(currentPos[0], currentPos[1], lastPos[0], lastPos[1]);
            lastPos = currentPos;
        }
    }

    //public void drawActionText(String[] text)

    //public void drawScene(String[] room)


    //public void drawPlayer(int x, y)
    

    //public void drawTriviaMenu(String question, String[] answers)


    //public void drawWumpus(int x, y)


    //public void drawObstacle(String[] room)
    

    //Returns an array of the current keys pressed.
    //public boolean[] getInput()



    
}
