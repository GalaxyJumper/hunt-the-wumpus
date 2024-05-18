import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Gui extends JPanel{
    //////////////////////////////////////
    //VARAIBLES
    //////////////////////////////////////
    Graphics2D g2d;
    int width, height;
    /////////////////////////////////////
    // CONSTRUCTOR(S)
    ////////////////////////////////////
    public Gui(String name, int width, int height, GameLocations locations){
        this.width = width;
        this.height = height;
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
        drawMap(0, 0, this.width / 20, g2d, 10);
        
    }
    private void drawHex(double centerX, double centerY, double radius, String number, Color color){
        double currentX = 0;
        double currentY = 0;
        int[] xPoints = new int[6];
        int[] yPoints = new int[6];
        for(int i = 0; i < 6; i++){
            currentX = centerX + (Math.cos((Math.PI/3) * i) * radius);
            currentY = centerY + (Math.sin((Math.PI/3) * i) * radius);
            xPoints[i] = (int)(currentX);
            yPoints[i] = (int)(currentY);
        }
        g2d.setColor(color);
        g2d.fillPolygon(xPoints, yPoints, 6);
        g2d.setColor(new Color(255, 255, 255));
        g2d.drawPolygon(xPoints, yPoints, 6);
        g2d.drawString(number, (int)centerX, (int)centerY);
    }
    private void drawMap(int startX, int startY, int radius, Graphics2D g2d, int playerLoc){
        //X = (even) startX + (3radius) * x
        //    (odd)  (startX + (3radius) * x) + 1.5 radius
        Color currentColor = new Color(0, 0, 0);
        for(int i = 0; i < 6; i++){
            double x = (i * (radius*1.5));
            for(int k = 0; k < 5; k++){ 
                double y = (k * (Math.sqrt(3) * radius));
                if(playerLoc == (k * 6) + i + 1){
                    currentColor = new Color(0, 255, 0);
                } else {
                    currentColor = new Color(20, 20, 20);
                }
                drawHex(x + startX, (y + ( (i % 2) * (Math.sqrt(3)*radius)/2) ) + startY, radius + 1, String.valueOf((k * 6) + i + 1), currentColor);
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
