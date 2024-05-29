import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
public class Gui extends JPanel implements MouseListener, ActionListener{
    //////////////////////////////////////
    //VARAIBLES
    //////////////////////////////////////
    Graphics2D g2d;
    int width, height;
    int mapStartX = 0;
    int mapStartY = 70;
    int mapRoomSize; // = width / 40;
    GameControl gameControl;
    int playerLoc;
    Font calibri;
    Font inconsolata;
    Cave cave;
    int trivChoice = -1;
    boolean disableClicks = false;

    Timer frameTimer = new Timer(17, this);
    
    // Drawing variables
    ArrayList<Integer> exploredRooms = new ArrayList<Integer> ();
    // {How long into the animation, location}
    // -1 if not drawing
    int[] failMoveHex = {-1, -1};
    // "Question?", "Answer 1", "Answer 2", "answer 3", "Answer 4"
    String[] triviaQuestion = new String[] {"Sample Question", "Answer 1", "Answer 2", "Answer 3", "Answer 4"};
    // {total # of Qs, q1 correct, q2 correct, q3 correct, q4 correct, q5 correct}
    int[] triviaScoreData = {-1, -1, -1, -1, -1, -1};
    // How transparent is the dimming in the background of Trivia?
    int dimRectTransparency = -1;
    // Which question this rectangle is highlighting. 0-3 inclusive when hovering over a Q, -1 otherwise
    int selectRectPos = -1;
    // Transistion rectangle animation variable
    int nextQTransitionDim = -1;
    // Dim of the rectangle that will tell you if you got the Q right or wrong
    int correctAnsRectDim = -1;
    // Which answer was selected (0-3) and whether it was right (1) or wrong (0). -1 otherwise.
    int[] selectedAnswerData = new int[] {-1, -1};
    int triviaMenuX, triviaMenuY, triviaMenuHeight, triviaMenuWidth = 3000;
    // Input variables
    boolean inTriviaMenu = false;
    String[] actionText = new String[] {"Entered room 23.", "Survived a Wumpus attack.", "You smell a foul stench.", "Gary requires attention.", "I am getting tired."};
    int[] actionTextFades = {255, 255, 255, 255, 255};
    Color[] actionTextColors = new Color[] {new Color(31, 31, 31), new Color(31, 31, 31), new Color(31, 31, 31), new Color(31, 31, 31), new Color(31, 31, 31)};

    ArrayList<Integer> tempFadeIndices = new ArrayList<Integer>();

    String b = new String("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
    /////////////////////////////////////
    // CONSTRUCTOR(S)
    ////////////////////////////////////
    public Gui(String name, int width, int height, Cave cave, GameControl gameControl, int playerLoc) throws FontFormatException, IOException{
        this.width = width;
        this.height = height;
        this.gameControl = gameControl;
        this.mapRoomSize = width / 20;
        this.mapStartX = (int)(mapRoomSize * 12) / 2;
        this.mapStartY = 200;
        this.cave = cave;
        this.playerLoc = playerLoc;
        frameTimer.start();
        //Create Calibri as a usable font
        File calibriFile = new File("calibri.ttf");
        calibri = Font.createFont(Font.TRUETYPE_FONT, calibriFile).deriveFont(30f);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(calibri);
        //Create Inconsolata as a usable font
        File inconsolataFile = new File("inconsolata.regular.ttf");
        inconsolata = Font.createFont(Font.TRUETYPE_FONT, inconsolataFile).deriveFont(30f);
        ge.registerFont(inconsolata);
        // Create a new Frame  for everything to live in
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
        this.failMove(2);
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
        //frame.pack();
        frame.setTitle(name);
        frame.setVisible(true);
    }
    /////////////////////////////////////
    // METHODS
    /////////////////////////////////////
    
    //Should run in a loop - gets called once every 17 ms
    //Draws things based on 


    ////////////////////////////////////////////////
    // PAINT
    ///////////////////////////////////////////////


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
        drawActionText(g2d);

        if(dimRectTransparency != -1){
            g2d.setColor(new Color(0, 0, 0, dimRectTransparency));
            g2d.fillRect(0, 0, width, height);
        }
        if(inTriviaMenu){
            drawTriviaMenu(this.triviaQuestion, new int[] {5, 3, 1, 2, 1, 0, 0}, g2d);
        }
        
        
    }
    ////////////////////////////////////////////////
    // MAP + MOVEMENT
   /////////////////////////////////////////////////

    private void drawRoom(double centerX, double centerY, double radius, String number, Color color){
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
        g2d.setStroke(new BasicStroke(2));
        g2d.drawPolygon(xPoints, yPoints, 6);
        g2d.drawString(number, (int)centerX, (int)centerY);
    }
    private void drawHex(double centerX, double centerY, double radius, Color color){
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
        g2d.drawPolygon(xPoints, yPoints, 6);
    }
    private void fillHex(double centerX, double centerY, double radius, Color color){
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
        for(int i = 0; i < 5; i++){
            
            double y = (i * (Math.sqrt(3) * radius));
            //Row
            for(int k = 0; k < 6; k++){ 
                double x = (k * (radius*1.5));
                int currentRoomNum = i * 6 + k;

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
                
                drawRoom(x + startX, (y + ( (k % 2) * (Math.sqrt(3)*radius)/2) ) + startY, radius + 1, String.valueOf(currentRoomNum), currentColor);
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
    ////////////////////////////////////////
    // ACTION TEXT
    ////////////////////////////////////////
    private void drawActionText(Graphics2D g2d){
        g2d.setFont(inconsolata.deriveFont(30f));
        Color textColor;
        for(int i = 0; i < actionText.length; i++){
            textColor = actionTextColors[i];
            g2d.setColor(
                new Color(textColor.getRed(), textColor.getGreen(), textColor.getBlue(), actionTextFades[i]));
            g2d.drawString(actionText[i], 70, 100 + (100 * i));    
        }
    }
    public void updateActionText(String message, Color color){
        System.out.println("actionText.length = " + actionText.length);
        for(int i = 1; i < actionText.length; i++){
            actionText[i - 1] = actionText[i];
            actionTextFades[i - 1] = actionTextFades[i];
            actionTextColors[i - 1] = actionTextColors[i];
        }
        actionText[4] = message;
        actionTextFades[4] = 220;
        actionTextColors[4] = color;
    }
    ///////////////////////////////////////
    // TRIVIA
    ///////////////////////////////////////
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
                g2d.setColor(new Color(255, 150, 0));
                g2d.fillOval(bubbleX, bubbleY, scoreBubbleSize, scoreBubbleSize);
            }
        }
        // "Trivia - X out of Y (Category)"
        g2d.setFont(calibri.deriveFont((float)40));
        g2d.setColor(Color.WHITE);
        g2d.drawString("Trivia - " + scoreData[1] + " out of " + scoreData[0], triviaMenuX + 20, triviaMenuY + 60);
        
        
        // Question
        int qTransparency = (nextQTransitionDim == 0)? 31 : 220;
        g2d.setColor(new Color(qTransparency, qTransparency, qTransparency));
        g2d.setFont(calibri.deriveFont((float)60));
        g2d.drawString(question[0], triviaMenuX + 30, triviaMenuY + triviaMenuHeight / 5);

        // Rectangle highlighting the curent answer hovered over
        if(selectRectPos != -1){
            int rectHeight = ((triviaMenuHeight * 3/4) - (triviaMenuHeight * 7/30)) / 4;
            int realY = triviaMenuY + (triviaMenuHeight / 4) + rectHeight * selectRectPos;
            g2d.setColor(new Color(43, 43, 43));
            g2d.fillRect(triviaMenuX, realY, triviaMenuWidth, rectHeight);
        }
        // Rectangle giving feedback on right/wrong answer
        if(correctAnsRectDim != -1){
            int rectHeight = ((triviaMenuHeight * 3/4) - (triviaMenuHeight * 7/30)) / 4;
            int realY = triviaMenuY + (triviaMenuHeight / 4) + rectHeight * selectedAnswerData[0];
            int correct = selectedAnswerData[1];
            
            g2d.setColor((correct == 1)? 
                new Color(10, 125, 25, correctAnsRectDim)
              : new Color(110, 35, 30, correctAnsRectDim)
              );
            g2d.fillRect(triviaMenuX, realY,  triviaMenuWidth, rectHeight);
        }
        // Answers
        int textTransparency = (nextQTransitionDim == -1)? 255 : nextQTransitionDim;
        g2d.setColor(new Color(220, 220, 220, textTransparency));
        g2d.setFont(calibri.deriveFont((float)40));
        String[] answerLabels = {"a)    ", "b)    ", "c)    ", "d)    "};
        for(int i = 0; i < 4; i ++){
            g2d.drawString(answerLabels[i] + question[i + 1], triviaMenuX + 50, (triviaMenuY + triviaMenuHeight / 3) + i * triviaMenuHeight/8);
        }
        //g2d.drawRect(triviaMenuX, triviaMenuY, triviaMenuWidth, triviaMenuHeight / 4);
        //g2d.drawRect(triviaMenuX, triviaMenuY + triviaMenuHeight * 23 / 30, triviaMenuWidth, (triviaMenuHeight * 7 / 30));
    }
    public void openTriviaMenu(String[] question, int numQs){
        long animationStart = System.currentTimeMillis();
        long now = System.currentTimeMillis();
        inTriviaMenu = true;
        this.triviaScoreData[0] = numQs;
        this.triviaScoreData[1] = (int)(numQs * 2/3);
        while(now - animationStart < 500){
            now = System.currentTimeMillis();
            dimRectTransparency = (int)(((double)now - (double)animationStart) / 500.0 * 150.0);
            if(now - animationStart > 250){
                this.triviaQuestion = question;
            }
            repaint();

        }
        
        
        
    }
    public void nextTriviaQuestion(boolean lastQCorrect, String[] nextQuestion, boolean isLastQ, int lastQNum){
        selectedAnswerData[1] = lastQCorrect? 1 : 0;
        long animStart = System.currentTimeMillis();
        long now = System.currentTimeMillis();
        disableClicks = true;
        while(now - animStart <= 1000){
            now = System.currentTimeMillis();
            correctAnsRectDim = (int)(1000 - (now - animStart));
            correctAnsRectDim = (correctAnsRectDim > 255)? 255 : correctAnsRectDim; 
            this.repaint();
        }
        animStart = System.currentTimeMillis();
        now = System.currentTimeMillis();
        if(isLastQ){
            this.closeTriviaMenu();
        }
        nextQTransitionDim = 0;
        this.triviaQuestion = nextQuestion;
        this.triviaScoreData[lastQNum] = (lastQCorrect)? 1 : 0;
        while(now - animStart <= 400){
            //Do nothing (delay)
            now = System.currentTimeMillis();
            this.repaint();
        }
        animStart = System.currentTimeMillis();
        now = System.currentTimeMillis();
        nextQTransitionDim = 1;
        while(now - animStart <= 700){
            nextQTransitionDim = (int)(255 * (now - animStart) / 700);

            now = System.currentTimeMillis();
            this.repaint();
        }
        disableClicks = false;

    }
    public void closeTriviaMenu(){
        inTriviaMenu = false;
        dimRectTransparency = -1;
        triviaQuestion = new String[6];
    }
    public String nextTriviaChoice(){
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
        String abcd = "ABCD";
        int temp = trivChoice;
        trivChoice = -1;
        selectRectPos = -1;
        selectedAnswerData[0] = temp;
        return abcd.substring(temp, temp + 1);
    }
    
    
    
    // Pirated from Avi's cave class
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
        updateActionText("Clicked", new Color(220, 220, 220));

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
        if(!disableClicks){
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

                        System.out.println(roomNumClicked + "here1");
                    }
                    else if(mouseY - hitBoxY < (mouseX - hitBoxX) * -Math.sqrt(3)){
                        System.out.println("Hit top triangle");
                    
                        roomNumClicked = (twoToOneD(mapInputY, mapInputX) - ((mapInputX % 2 == 0)? 7 : 1)) % 30;

                        if(roomNumClicked < 0){
                            roomNumClicked += 30;
                        }
                        
                        System.out.println(roomNumClicked + "here2");
                        
                    } else {
                        roomNumClicked = twoToOneD(mapInputY, mapInputX);
                        System.out.println(roomNumClicked + "here3");
                    }
                    System.out.println("here4");
                    updateActionText("I'm here5", new Color(255,255,255));
                    System.out.println("here6");
                    gameControl.turn(roomNumClicked);
                    updateActionText("I'm here7", new Color(255,255,255));
                    
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
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void mousePressed(MouseEvent e){

    }
    public void mouseEntered(MouseEvent e){

    }
    public void mouseExited(MouseEvent e){

    }
    public void mouseReleased(MouseEvent e){

    }
    // Used for constantly ongoing animation such as action text fading out.
    public void actionPerformed(ActionEvent e){
        for(int i = 0; i < actionTextFades.length; i++){
            if(actionTextFades[i] > 30){
                actionTextFades[i] -= 1.3;
            }
        }
        this.repaint();
    }
}
