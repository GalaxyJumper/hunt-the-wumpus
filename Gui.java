import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
public class Gui extends JPanel implements MouseListener{
    //////////////////////////////////////
    //VARAIBLES
    //////////////////////////////////////
    Graphics2D g2d;
    int width, height;
    int mapStartX = 0;
    int mapStartY = 70;
    int mapRoomSize; // = width / 40;
    GameLocations gameLocs;
    GameControl gameControl;
    int playerLoc;
    Font calibri;
    Cave cave;
    int trivChoice = -1;
    // Drawing variables
    ArrayList<Integer> exploredRooms = new ArrayList<Integer> ();
    // {How long into the animation, location}
    // -1 if not drawing
    int[] failMoveHex = {-1, -1};
    // "Question?", "Genre", "Answer 1", "Answer 2", "answer 3", "Answer 4"
    String[] triviaQuestion = new String[6];
    // {total # of Qs, number of correct answers required, q1 correct, q2 correct, q3 correct, q4 correct, q5 correct}
    int[] triviaScoreData = {-1, -1, -1, -1, -1, -1, -1};
    // How transparent is the dimming on the menu?
    int dimRectTransparency = -1;
    //
    int selectRectPos = -1;
    
    int triviaMenuX, triviaMenuY, triviaMenuHeight, triviaMenuWidth = 3000;
    // Input variables
    boolean inTriviaMenu = false;
    /////////////////////////////////////
    // CONSTRUCTOR(S)
    ////////////////////////////////////
    public Gui(String name, int width, int height, GameLocations locations, GameControl gameControl) throws FontFormatException, IOException{
        this.width = width;
        this.height = height;
        this.gameLocs = locations;
        this.gameControl = gameControl;
        this.mapRoomSize = width / 20;
        this.mapStartX = (int)(mapRoomSize * 12) / 2;
        this.mapStartY = 200;
        this.cave = gameLocs.getCave();
        //Create Calibri as a usable font
        File calibriFile = new File("calibri.ttf");
        calibri = Font.createFont(Font.TRUETYPE_FONT, calibriFile).deriveFont(30f);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(calibri);

        // Create a new Frame for everything to live in
        JFrame frame = new JFrame();
        // Debug message
        System.out.println("New display instantiated with dimensions " + width + "x" + height);
        // Set display size
        this.setPreferredSize(new Dimension(width, height));
        frame.setPreferredSize(new Dimension(width, height));
        // Set default close operation (end program once the window is closed)
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // for later 
        this.addMouseListener(this);



        //Put this panel into its frame so it can be displayed
        frame.add(this);
        //Make frame fit into the screen
        frame.pack();
        //Make the window visible and set its name to the given name
        frame.setTitle(name);
        frame.setVisible(true);
        this.move(23);
        this.failMove(2);
        this.openTriviaMenu(new String[][] {{"bruh"}, {"bruh"}});
        //this.closeTriviaMenu(); 
        System.out.println(this.nextTriviaChoice() + " was the choice");
        new String("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
        new String("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
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
        
        //Antialiasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
RenderingHints.VALUE_ANTIALIAS_ON); 
        //Background + map
        g2d.setColor(new Color(10, 10, 10));
        g2d.setFont(calibri);
        g2d.fillRect(0, 0, width, height);
        drawMap(mapStartX, mapStartY, mapRoomSize, g2d, playerLoc);

        if(failMoveHex[0] != -1 && failMoveHex[1] != -1){
            drawFailMoveHex(failMoveHex[0], failMoveHex[1]);
        }
        if(dimRectTransparency != -1){
            g2d.setColor(new Color(0, 0, 0, dimRectTransparency));
            g2d.fillRect(0, 0, width, height);
        }
        if(inTriviaMenu){
            drawTriviaMenu(new String[] {"Chicago is a ______", "Geography", "State", "County", "City", "Continent"}, new int[] {5, 3, 1, 2, 1, 0, 0}, g2d);
        }
        
    }
    /*
     * drawRoom
     * 
     * centerX, Y: Center of the hexagon.
     * Radius: distance from center to any vertex.
     * number: Which number to label the room.
     * Color: What color to draw the room.
     * 
     *          1_
     *      2 /    \ 0
     *      3 \ __ / 5
     *           4
     */
    private void drawRoom(double centerX, double centerY, double radius, String number, Color color){
        double currentX = 0;
        double currentY = 0;
        int[] xPoints = new int[6];
        int[] yPoints = new int[6];
        double doorScale = 0.3;
        for(int i = 0; i < 6; i++){
            currentX = centerX + (Math.cos((Math.PI/3) * i) * radius);
            currentY = centerY + (Math.sin((Math.PI/3) * i) * radius);
            xPoints[i] = (int)(currentX);
            yPoints[i] = (int)(currentY);
        }
        g2d.setColor(color);
        g2d.fillPolygon(xPoints, yPoints, 6);
        g2d.setColor(new Color(255, 255, 255));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawPolygon(xPoints, yPoints, 6);
        g2d.drawString(number, (int)centerX, (int)centerY);
    }

    private void drawHex(double centerX, double centerY, double radius, Color color){
        double currentX = 0;
        double currentY = 0;
        int[] xPoints = new int[6];
        int[] yPoints = new int[6];
        double doorScale = 0.3;
        for(int i = 0; i < 6; i++){
            currentX = centerX + (Math.cos((Math.PI/3) * i) * radius);
            currentY = centerY + (Math.sin((Math.PI/3) * i) * radius);
            xPoints[i] = (int)(currentX);
            yPoints[i] = (int)(currentY);
        }
        g2d.setColor(color);
        g2d.drawPolygon(xPoints, yPoints, 6);
    }

    private void fillHex(double centerX, double centerY, double radius, Color color){
        double currentX = 0;
        double currentY = 0;
        int[] xPoints = new int[6];
        int[] yPoints = new int[6];
        double doorScale = 0.3;
        for(int i = 0; i < 6; i++){
            currentX = centerX + (Math.cos((Math.PI/3) * i) * radius);
            currentY = centerY + (Math.sin((Math.PI/3) * i) * radius);
            xPoints[i] = (int)(currentX);
            yPoints[i] = (int)(currentY);
        }
        g2d.setColor(color);
        g2d.fillPolygon(xPoints, yPoints, 6);
    }

    private void drawMap(int startX, int startY, int radius, Graphics2D g2d, int playerLoc){
        //X = (even) startX + (3radius) * x
        //    (odd)  (startX + (3radius) * x) + 1.5 radius
        Color currentColor = new Color(0, 0, 0);
        

        int[] possibleMovesInt = cave.possibleMoves(playerLoc);
        ArrayList<Integer> possibleMoves = new ArrayList<Integer>();
        ArrayList<Integer> exploredRoomsTemp = exploredRooms;
        for(int i : possibleMovesInt) possibleMoves.add(i);
        //Column
        for(int i = 0; i < 6; i++){
            double x = (i * (radius*1.5));
            //Row
            for(int k = 0; k < 5; k++){ 
                double y = (k * (Math.sqrt(3) * radius));
                int currentRoomNum = k * 6 + i;

                if(playerLoc == currentRoomNum){
                    currentColor = new Color(0, 255, 0);
                } else if(possibleMoves.size() > 0 && possibleMoves.get(0) == currentRoomNum){
                    currentColor = new Color(60, 60, 60);
                    possibleMoves.remove(0);
                } else if(exploredRoomsTemp.size() > 0 && exploredRoomsTemp.get(0) == currentRoomNum){
                    currentColor = new Color(60, 60, 60);
                    exploredRoomsTemp.remove(0);
                } 
                else {
                    currentColor = new Color(20, 20, 20);
                }
                
                drawRoom(x + startX, (y + ( (i % 2) * (Math.sqrt(3)*radius)/2) ) + startY, radius + 1, String.valueOf(currentRoomNum + 1), currentColor);
            }
        }
    
    }

    private void drawFailMoveHex(int millis, int loc){
        int x = loc % 6;
        int y = loc / 6;
        int drawX = (int)(x * mapRoomSize * 1.5 + mapStartX);
        int drawY = (int)(y + mapStartY + ((y % 2) * (Math.sqrt(3) * (mapRoomSize + 1))/2));
        double currentTransparency = ((double)millis / 500) * 255;
        Color currentColor = new Color(255, 0, 0, (int)(255 - currentTransparency));
        fillHex(drawX, drawY, mapRoomSize - 2, currentColor);
        
    }

    public void move(int whereTo){
        playerLoc = whereTo;
        this.repaint();

    }

    public void failMove(int whereTo){
        long animationStart = System.currentTimeMillis();
        long now = System.currentTimeMillis();
        while(now - animationStart < 500){
            now = System.currentTimeMillis();
            failMoveHex[0] = (int)(now - animationStart);
            failMoveHex[1] = whereTo;
            this.repaint();
        }
        failMoveHex[0] = -1;
        failMoveHex[1] = -1;
    }

    private void drawTriviaMenu(String[] question, int[] scoreData, Graphics2D g2d){
        triviaMenuWidth = (this.width / 2);
        triviaMenuHeight = (this.height * 2 / 3);
        triviaMenuX = (this.width - triviaMenuWidth) / 2;
        triviaMenuY = (this.height - triviaMenuHeight) / 2;
        int scoreBubbleSize = 30;
        //Base menu background
        g2d.setColor(new Color(31, 31, 31));
        g2d.fillRect(triviaMenuX, triviaMenuY, triviaMenuWidth, triviaMenuHeight);
        //Top darker section
        g2d.setColor(new Color(24, 24, 24));
        g2d.fillRect(triviaMenuX, triviaMenuY, triviaMenuWidth, triviaMenuHeight / 10);
        //Lighter border
        g2d.setColor(new Color(43, 43, 43));
        g2d.fillRect(triviaMenuX, triviaMenuY + triviaMenuHeight / 10, triviaMenuWidth, triviaMenuHeight / 60);
        //Bottom border
        g2d.setColor(new Color(43, 43, 43));
        g2d.fillRect(triviaMenuX, triviaMenuY + triviaMenuHeight * 59 / 60, triviaMenuWidth, triviaMenuHeight / 60);
        //Question bubbles
        for(int i = 0; i < scoreData[0]; i++){

            int bubbleX = (triviaMenuX + triviaMenuWidth) - (60 * (scoreData[0] - i));
            int bubbleY = (triviaMenuY + (triviaMenuHeight / 10 - scoreBubbleSize) / 2);

            //Question unanswered
            if(scoreData[i + 2] == 0){
                g2d.setColor(new Color(220, 220, 220));
                g2d.drawOval(bubbleX, bubbleY, scoreBubbleSize, scoreBubbleSize);
            }
            //Question correct
            else if(scoreData[i + 2] == 1){
                g2d.setColor(new Color(0, 220, 0));
                g2d.fillOval(bubbleX, bubbleY, scoreBubbleSize, scoreBubbleSize);
            }
            //Question incorrect
            else if(scoreData[i + 2] == 2){
                g2d.setColor(new Color(255, 165, 0));
                g2d.fillOval(bubbleX, bubbleY, scoreBubbleSize, scoreBubbleSize);
            }
        }
        // "Trivia - X out of Y (Category)"
        g2d.setFont(calibri.deriveFont((float)40));
        g2d.setColor(Color.WHITE);
        g2d.drawString("Trivia - " + scoreData[1] + " out of " + scoreData[0] + " (" + question[1] + ")", triviaMenuX + 20, triviaMenuY + 60);
        // Question
        g2d.setFont(calibri.deriveFont((float)60));
        g2d.drawString(question[0], triviaMenuX + 30, triviaMenuY + triviaMenuHeight / 5);

        //
        if(selectRectPos != -1){
            int rectHeight = ((triviaMenuHeight * 3/4) - (triviaMenuHeight * 7/30)) / 4;
            int realY = triviaMenuY + (triviaMenuHeight / 4) + rectHeight * selectRectPos;
            g2d.setColor(new Color(43, 43, 43));
            g2d.fillRect(triviaMenuX, realY, triviaMenuWidth, rectHeight);
        }




        // Answers
        g2d.setColor(new Color(220, 220, 220));
        g2d.setFont(calibri.deriveFont((float)40));
        String[] answerLabels = {"a)    ", "b)    ", "c)    ", "d)    "};
        for(int i = 0; i < 4; i ++){
            g2d.drawString(answerLabels[i] + question[i + 2], triviaMenuX + 50, (triviaMenuY + triviaMenuHeight / 3) + i * triviaMenuHeight/8);
        }
        //g2d.drawRect(triviaMenuX, triviaMenuY, triviaMenuWidth, triviaMenuHeight / 4);
        //g2d.drawRect(triviaMenuX, triviaMenuY + triviaMenuHeight * 23 / 30, triviaMenuWidth, (triviaMenuHeight * 7 / 30));
    }

    public void openTriviaMenu(String[][] triviaQuestions){
        long animationStart = System.currentTimeMillis();
        long now = System.currentTimeMillis();
        inTriviaMenu = true;
        while(now - animationStart < 500){
            now = System.currentTimeMillis();
            dimRectTransparency = (int)(((double)now - (double)animationStart) / 500.0 * 150.0);
            if(now - animationStart > 250){
                //this.triviaQuestion = triviaQuestion;
                //triviaScoreData[0] = numQuestions;
            }
            repaint();

        }
        
        
        
    }
    public void nextTriviaQuestion(boolean lastQCorrect){
        
    }
    public void closeTriviaMenu(){
        inTriviaMenu = false;
        dimRectTransparency = -1;
        triviaQuestion = new String[6];
    }
    // Pirated from Avi's cave class
    private int[] oneToTwoD(int oneDCoord){
        int[] twoD = new int[]{oneDCoord / 6, oneDCoord % 6};
        return twoD;
    }

    private int twoToOneD(int y, int x){
        return x + (6 * y);
    }
    
    ////////////////////////////////////////////////
    // MOUSE METHODS
    ////////////////////////////////////////////////
    public void mouseClicked(MouseEvent e){
        System.out.print("GUI: Player clicked");
        double mouseX = e.getX();
        double mouseY = e.getY();
        System.out.println(" at " + mouseX + ", " + mouseY);


        /////////// MAP INPUT ////////////
        /* 
         * Uses a tiling grid of rectangles to represent hexagons. 
         * Each extends from the very left edge of the hexagon to the second-rightmost vertex.
         * Each rectangle overlaps with two neighboring hexagons - the ones to the top and bottom left of this hexagon, like so:
         *  _____
         * |/    |\____|/
         * |\____|/    |\
         * |/    |\____|/
         * |\____|/    |\
         * 
         * This way each of the bounding boxes looks like this:
         *  ____________                     
         * | /          |                  
         * |/           |                  
         * |\           |
         * |_\__________|
         * By checking both of the triangles we can figure out whether the mouse is in this hexagon or one of its neighbors.
        */
        if(!inTriviaMenu){
            double mapLeftEdge = mapStartX - (mapRoomSize);
            double mapTopEdge = mapStartY - (mapRoomSize);
            double mapRoomHeight = (mapRoomSize) * Math.sqrt(3);
            
            int mapInputX = (int)((mouseX - mapLeftEdge) / (1.5 * mapRoomSize));
            int mapInputY = (int)((mouseY - mapTopEdge - (mapInputX % 2 * (0.5 * mapRoomHeight))) / (mapRoomHeight));

            int roomNumClicked = 99;
            if(mouseX < mapLeftEdge || mouseX > mapLeftEdge + 9.5 * mapRoomSize){
                return;
            }
            if(mouseY < mapTopEdge || mouseY > 5.5 * mapRoomHeight){
                return;
            }
            else {
                double hitBoxX = mapLeftEdge + (mapInputX * (mapRoomSize * 1.5));
                double hitBoxY = mapTopEdge + (mapInputX % 2 * (0.5 * mapRoomHeight)) + (mapRoomHeight / 2) + (mapRoomHeight * mapInputY) + 15;
                System.out.println("Hitbox Triangle Pos: " + hitBoxX + ", " + hitBoxY);
                if(mouseY - hitBoxY > (mouseX - hitBoxX) * Math.sqrt(3)){
                    
                    //Special case - the room at the triangle's location will be at a different Y than this hexagon.
                    if(mapInputX == 0){
                        roomNumClicked = (twoToOneD(mapInputY, mapInputX) + 5) % 30;
                    }

                    else {
                        roomNumClicked = (twoToOneD(mapInputY, mapInputX) + ((mapInputX % 2 == 0)? -1 : 5)) % 30;
                    }

                    if(roomNumClicked < 0){
                        roomNumClicked += 30;
                    }

                    System.out.println(roomNumClicked);
                }
                else if(mouseY - hitBoxY < (mouseX - hitBoxX) * -Math.sqrt(3)){
                    System.out.println("Hit top triangle");
                
                    roomNumClicked = (twoToOneD(mapInputY, mapInputX) - ((mapInputX % 2 == 0)? 7 : 1)) % 30;

                    if(roomNumClicked < 0){
                        roomNumClicked += 30;
                    }
                    
                    System.out.println(roomNumClicked);
                    
                } else {
                    roomNumClicked = twoToOneD(mapInputY, mapInputX);
                    System.out.println(roomNumClicked);
                }
                gameControl.turn(roomNumClicked);
                this.move(roomNumClicked);
            }

        }
         else if (inTriviaMenu) { 
            System.out.println(triviaMenuX);
            System.out.println(triviaMenuX + triviaMenuWidth);
            System.out.println(triviaMenuY + triviaMenuHeight / 4);
            System.out.println(triviaMenuY + (triviaMenuHeight * 23 / 30));
            if(mouseX > triviaMenuX && 
               mouseX < triviaMenuX + triviaMenuWidth &&
               mouseY > triviaMenuY + triviaMenuHeight / 4 
               && mouseY < triviaMenuY + (triviaMenuHeight * 23 / 30 )){
                
                double answerSelectionHeight = ((triviaMenuHeight * 3 / 4) - (triviaMenuHeight * 7 / 30));

                double answerHitboxHeight = answerSelectionHeight / 4;

                int answerSelected = (int)((mouseY - triviaMenuY - (triviaMenuHeight / 4)) / answerHitboxHeight);
                trivChoice = answerSelected;
            }
        }
    }
    
    public int nextTriviaChoice(){
        double mouseX, mouseY;
        
        double answerSelectionHeight = ((triviaMenuHeight * 3 / 4) - (triviaMenuHeight * 7 / 30));

        double answerHitboxHeight = answerSelectionHeight / 4;

        while(trivChoice == -1){
            mouseX = MouseInfo.getPointerInfo().getLocation().getX();    
            mouseY = MouseInfo.getPointerInfo().getLocation().getY();
            
            if(mouseX > triviaMenuX && 
               mouseX < triviaMenuX + triviaMenuWidth &&
               mouseY > triviaMenuY + triviaMenuHeight / 4 + (answerHitboxHeight / 2)
               && mouseY < triviaMenuY + (triviaMenuHeight * 23 / 30 ) + (answerHitboxHeight / 2)){
                

                int answerHovered = (int)((mouseY - triviaMenuY - (triviaMenuHeight / 4) - (answerHitboxHeight / 2)) / answerHitboxHeight);
                selectRectPos = answerHovered;
            } else {
                selectRectPos = -1;
            }
            this.repaint();
        }
        int temp = trivChoice;
        trivChoice = -1;
        selectRectPos = -1;
        return temp;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void mousePressed(MouseEvent e){

    }
    public void mouseEntered(MouseEvent e){

    }
    public void mouseExited(MouseEvent e){

    }
    public void mouseReleased(MouseEvent e){

    }
    
}
