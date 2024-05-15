import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Gui extends JPanel{
    //////////////////////////////////////
    //VARAIBLES
    //////////////////////////////////////
    Graphics2D g2d;
    boolean drawTriviaMenu, todo = false;
    /////////////////////////////////////
    // CONSTRUCTOR(S)
    ////////////////////////////////////
    public Gui(String name, int width, int height, GameLocations locations){
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
    
    //Should run in a loop - gets called once every 17 ms
    //Draws things based on 
    public void paint(Graphics g){
        g2d = (Graphics2D)g;
        drawHexGrid(300, 100, 50, g2d);
        
    }
    private void drawHex(double centerX, double centerY, double radius, String number){
        double lastX = centerX + radius;
        double lastY = centerY;
        double currentX = 0;
        double currentY = 0;
        for(int i = -1; i < 6; i++){
            currentX = centerX + (Math.cos((Math.PI/3) * i) * radius);
            currentY = centerY + (Math.sin((Math.PI/3) * i) * radius);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine((int)currentX, (int)currentY, (int)lastX, (int)lastY);
            lastX = currentX;
            lastY = currentY;
        }
        g2d.drawString(number, (int)centerX, (int)centerY);
    }
    private void drawHexGrid(int startX, int startY, int radius, Graphics2D g2d){
        //X = (even) startX + (3radius) * x
        //    (odd)  (startX + (3radius) * x) + 1.5 radius
        for(int i = 0; i < 6; i++){
            double x = (i * (radius*1.5));
            for(int k = 0; k < 5; k++){ 
                double y = (k * (Math.sqrt(3) * radius));
                drawHex(x + startX, (y + ( (i % 2) * (Math.sqrt(3)*radius)/2) ) + startY, radius, String.valueOf((k * 6) + i + 1));
                //draw the player
            }
        }
    
    }
    public void updateMapDisplay(int currentRoom){

    }
    public void openTriviaWindow(){
        
    }
    public void closeTriviaWindow(){
        
    }
    //public void drawActionText(String[] text) LATER

    //public void drawScene(String[] room) LATER


    //public void drawPlayer(int x, y) LATER
    


    //public void drawWumpus(int x, y)


    //public void drawObstacle(String[] room)




    
}
