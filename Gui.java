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
    String[] triviaQuestion = new String[] {"", "", "", "", ""};
    // {total # of Qs, q1 correct, q2 correct, q3 correct, q4 correct, q5 correct}
    int[] triviaScoreData = {-1, -1, -1, -1, -1, -1, -1};
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
    
    long moveAnimStart = -1;

    String triviaCause;

    long triviaMenuOpened = 0;

    long triviaFeedbackAnimStart = 0;

    long now;

    long failMoveStart = 0;

    boolean isLastQ;

    String[] tempQuestion;

    String[] actionText = new String[] {"Entered room 23.", "Survived a Wumpus attack.", "You smell a foul stench.", "Gary requires attention.", "I am getting tired."};
    int[] actionTextFades = {255, 255, 255, 255, 255};
    Color[] actionTextColors = new Color[] {new Color(31, 31, 31), new Color(31, 31, 31), new Color(31, 31, 31), new Color(31, 31, 31), new Color(31, 31, 31)};
    ArrayList<Integer> tempFadeIndices = new ArrayList<Integer>();
    int testCounter = 0;

    String b = new String("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
    SoundManager sounds = new SoundManager();

    double[] mapOffset = new double[] {0, 0};
    double[] distanceMovingTo = new double[] {0, 0};
    double[] lastOffset;

    int[] mousePos = new int[2];
    /////////////////////////////////////
    // CONSTRUCTOR(S)
    ////////////////////////////////////
    public Gui(String name, int width, int height, Cave cave, GameControl gameControl, int playerLoc) throws FontFormatException, IOException{
        this.width = width;
        this.height = height;
        this.gameControl = gameControl;
        this.mapRoomSize = width / 6;
        this.mapStartX = (int)(mapRoomSize * 12) / 2;
        this.mapStartY = 200;
        this.cave = cave;
        this.playerLoc = playerLoc;
        int[] tempPlayerLoc = oneToTwoD(playerLoc);
        double[] playerScreenLoc = twoDToScreenSpace(tempPlayerLoc[0], tempPlayerLoc[1]);
        mapOffset = new double[] {-playerScreenLoc[0] + 1280, -playerScreenLoc[1] + 720};
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
        sounds.playSound(2);
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
        drawMap((int)(mapStartX + mapOffset[0]), (int)(mapStartY + mapOffset[1]), mapRoomSize, g2d, playerLoc);
        drawMap((int)(mapStartX + mapOffset[0] - (mapRoomSize * 9)), (int)(mapStartY + mapOffset[1]), mapRoomSize, g2d, playerLoc);
        drawMap((int)(mapStartX + mapOffset[0] + (mapRoomSize * 9)), (int)(mapStartY + mapOffset[1]), mapRoomSize, g2d, playerLoc);

        if(failMoveHex[0] != -1 && failMoveHex[1] != -1){
            drawFailMoveHex(failMoveHex[0], failMoveHex[1]);
        
        }
        drawActionText(g2d);

        if(dimRectTransparency != -1){
            g2d.setColor(new Color(0, 0, 0, dimRectTransparency));
            g2d.fillRect(0, 0, width, height);
        }
        if(inTriviaMenu){
            drawTriviaMenu(this.triviaQuestion, this.triviaScoreData, g2d);
        }
        g2d.setColor(new Color(255, 255, 255));
        g2d.drawString("" + testCounter, 300, 90);
        g2d.drawString(mousePos[0] + ", " + mousePos[1], 300, 140);
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
        int drawX = (int)(x * mapRoomSize * 1.5 + mapStartX + mapOffset[0]);
        int drawY = (int)(y + mapStartY + mapOffset[1] + ((y % 2) * (Math.sqrt(3) * (mapRoomSize + 1))/2));
        double currentTransparency = ((double)millis / 500) * 255;
        Color currentColor = new Color(255, 0, 0, (int)(255 - currentTransparency));
        fillHex(drawX, drawY, mapRoomSize - 2, currentColor);
        
    }
    public void move(int whereTo){
        playerLoc = whereTo;
        this.repaint();
        disableClicks = true;
        moveAnimStart = System.currentTimeMillis();
        int[] mapCoords = oneToTwoD(whereTo);
        double[] moveToCoords = twoDToScreenSpace(mapCoords[0], mapCoords[1]);
        distanceMovingTo = new double[] {1280 - moveToCoords[0], 720 - moveToCoords[1]};
        lastOffset = this.mapOffset;

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
        g2d.setFont(inconsolata.deriveFont(50f));
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
        actionTextFades[4] = 255;
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
            if(scoreData[i + 2] == -1){
                g2d.setColor(new Color(220, 220, 220));
                g2d.drawOval(bubbleX, bubbleY, scoreBubbleSize, scoreBubbleSize);
            }
            //Question correct
            else if(scoreData[i + 2] == 1){
                g2d.setColor(new Color(0, 220, 0));
                g2d.fillOval(bubbleX, bubbleY, scoreBubbleSize, scoreBubbleSize);
            }
            //Question incorrect
            else if(scoreData[i + 2] == 0){
                g2d.setColor(new Color(255, 150, 0));
                g2d.fillOval(bubbleX, bubbleY, scoreBubbleSize, scoreBubbleSize);
            }
        }
        // "Trivia - X out of Y (Category)"
        g2d.setFont(calibri.deriveFont((float)40));
        g2d.setColor(Color.WHITE);
        g2d.drawString(triviaCause + " - get " + scoreData[1] + " out of " + scoreData[0] + " correct", triviaMenuX + 20, triviaMenuY + 60);
        
        
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
    public void openTriviaMenu(String why, String[] question, int numQs){
        triviaMenuOpened = System.currentTimeMillis();
        this.triviaScoreData[0] = numQs;
        this.triviaScoreData[1] = (int)(numQs * 2/3);
        this.trivChoice = -1;
        this.triviaQuestion = question;
        nextQTransitionDim = -1;
        triviaCause = why;
        
        
        
    }
    public void nextTriviaQuestion(boolean lastQCorrect, String[] nextQuestion, boolean isLastQ, int lastQNum){
        selectedAnswerData[1] = lastQCorrect? 1 : 0;
        disableClicks = true;
        this.isLastQ = isLastQ;
        nextQTransitionDim = -1;
        this.tempQuestion = nextQuestion;
        this.triviaScoreData[5 - lastQNum + 1] = (lastQCorrect)? 1 : 0; 
        triviaFeedbackAnimStart = System.currentTimeMillis();
        if(lastQCorrect){
            sounds.playSound(4);
        } else {
            sounds.playSound(3);
        }

    }
    public void closeTriviaMenu(){
        inTriviaMenu = false;
        dimRectTransparency = -1;
        triviaQuestion = new String[] {"", "", "", "", "", ""};
        triviaScoreData = new int[] {-1, -1, -1, -1, -1, -1, -1};
        correctAnsRectDim = -1;
    }

    
    
    
    // Pirated from Avi's cave class
    private int twoToOneD(int y, int x){
        return x + (6 * y);
    }
    private int[] oneToTwoD(int index){
        return new int[] {
            (index % 6),
            (index / 6)
        };

    }
    private double[] twoDToScreenSpace(int x, int y){
        double resultY = (y * (Math.sqrt(3) * mapRoomSize));
        double resultX = (x * (mapRoomSize*1.5));
        return new double[] {
            resultX + mapStartX + mapOffset[0],
            (resultY + ((x % 2) * (Math.sqrt(3)*mapRoomSize)/2) ) + mapStartY + mapOffset[1]
        };
    }
    
    
    ////////////////////////////////////////////////
    // MOUSE METHODS
    ////////////////////////////////////////////////
    public void mouseClicked(MouseEvent e){
        double answerSelectionHeight = ((triviaMenuHeight * 3 / 4) - (triviaMenuHeight * 7 / 30));
        double answerHitboxHeight = answerSelectionHeight / 4;
        this.repaint();
        double mouseX = e.getX();
        double mouseY = e.getY();
        int buttonClicked = e.getButton();
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
                double mapLeftEdge = mapStartX + mapOffset[0] - (mapRoomSize);
                double mapTopEdge = mapStartY + mapOffset[1] - (mapRoomSize);
                double mapRoomHeight = (mapRoomSize) * Math.sqrt(3);
                
                int mapInputX = (int)((mouseX - mapLeftEdge) / (1.5 * mapRoomSize));
                int mapInputY = (int)((mouseY - mapTopEdge - (mapInputX % 2 * (0.5 * mapRoomHeight))) / (mapRoomHeight));

                int roomNumClicked = 99;
                    double hitBoxX = mapLeftEdge + (mapInputX * (mapRoomSize * 1.5));
                    double hitBoxY = mapTopEdge + (mapInputX % 2 * (0.5 * mapRoomHeight)) + (mapRoomHeight / 2) + (mapRoomHeight * mapInputY) + (mapRoomSize * 15 / 128);
                    if(mouseY - hitBoxY > (mouseX - hitBoxX) * Math.sqrt(3)){
                        System.out.println("Hit bottom triangle");
                        //Bottom triangle
                        //Special case - the room at the triangle's location will be at a different Y than this hexagon.
                        if(mapInputX == 0){
                            roomNumClicked = (twoToOneD(mapInputY, 5));
                            mapStartX -= (mapRoomSize * 9);
                        }

                        else {
                            roomNumClicked = (twoToOneD(mapInputY, mapInputX) + ((mapInputX % 2 == 0)? -1 : 5)) % 30;
                        }

                        if(roomNumClicked < 0){
                            roomNumClicked += 30;
                        }

                        
                    }
                    else if(mouseY - hitBoxY < (mouseX - hitBoxX) * -Math.sqrt(3)){
                        // TOP triangle
                        System.out.println("Hit top triangle");
                        roomNumClicked = (twoToOneD(mapInputY, mapInputX) - ((mapInputX % 2 == 0)? 7 : 1)) % 30;

                        if(roomNumClicked < 0){
                            roomNumClicked += 30;
                            mapStartX -= (mapRoomSize * 9);
                        }
                        

                        
                    } else {
                        roomNumClicked = twoToOneD(mapInputY, mapInputX);
                    }
                    if(buttonClicked == 1){
                        double[] screenSpaceCoords = twoDToScreenSpace(mapInputX, mapInputY);
                        gameControl.turn(roomNumClicked);
                    } 
                    else if(buttonClicked == 2){
                        gameControl.turn(roomNumClicked, true);
                    }
                    
                

            }

            /////////////////////////////////////////////////
            // TRIVIA INPUT
            //////////////////////////////////////////////////


            else if (inTriviaMenu) { 
                System.out.println(triviaMenuX);
                System.out.println(triviaMenuX + triviaMenuWidth);
                System.out.println(triviaMenuY + triviaMenuHeight / 4);
                System.out.println(triviaMenuY + (triviaMenuHeight * 23 / 30));
                if(mouseX > triviaMenuX && 
                mouseX < triviaMenuX + triviaMenuWidth &&
                mouseY > triviaMenuY + triviaMenuHeight / 4 
                && mouseY < triviaMenuY + (triviaMenuHeight * 23 / 30 )){
                    
                    answerSelectionHeight = ((triviaMenuHeight * 3 / 4) - (triviaMenuHeight * 7 / 30));

                    answerHitboxHeight = answerSelectionHeight / 4;

                    int answerSelected = (int)((mouseY - triviaMenuY - (triviaMenuHeight / 4)) / answerHitboxHeight);
                    trivChoice = answerSelected;
                }
                String abcd = "ABCD";
                int temp = trivChoice;
                trivChoice = -1;
                selectRectPos = -1;
                selectedAnswerData[0] = temp;
                gameControl.questionAnswer(abcd.substring(temp, temp + 1));

                

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
        // Animation veriables for moving the player
        int r = mapRoomSize; // Radius of map hexagons
        double t; // Time elapsed
        double D = 1;
        double d;
        
        now = System.currentTimeMillis();
        //Trivia fade-in animation
        if(now - triviaMenuOpened < 500){
            dimRectTransparency = (int)(((double)now - (double)triviaMenuOpened) / 500.0 * 200);
            if(now - triviaMenuOpened > 250){
                this.inTriviaMenu = true;
            }
            repaint();

        } else {
            triviaMenuOpened = 0;
        }
        //Trivia right/wrong
        if(now - triviaFeedbackAnimStart <= 2100){
            
            correctAnsRectDim = (int)(1000 - (now - triviaFeedbackAnimStart));
            correctAnsRectDim = (correctAnsRectDim > 255)? 255 : correctAnsRectDim; 
            correctAnsRectDim = (correctAnsRectDim < 0)? 0 : correctAnsRectDim;
            if(now - triviaFeedbackAnimStart < 1000){
                nextQTransitionDim = 255;
            }
            if(now - triviaFeedbackAnimStart > 1000 && now - triviaFeedbackAnimStart < 1400){
                    if(isLastQ){
                        triviaFeedbackAnimStart = 0;
                        gameControl.continueTurn();
                        this.closeTriviaMenu();;;
                        disableClicks = false;
                    }
                    nextQTransitionDim = 0;
            }
            else if(now - triviaFeedbackAnimStart > 1400 && now - triviaFeedbackAnimStart < 2100){
                isLastQ = false;
                this.triviaQuestion = tempQuestion;
                nextQTransitionDim = (int)(255 * (now - (triviaFeedbackAnimStart + 1400)) / 700);
                nextQTransitionDim = (nextQTransitionDim > 255)? 255 : nextQTransitionDim; 
                nextQTransitionDim = (nextQTransitionDim < 0)? 0 : nextQTransitionDim;
            }
            if(now - triviaFeedbackAnimStart > 2050 && now - triviaFeedbackAnimStart < 2100){
                disableClicks = false;
            }
        }

        double answerSelectionHeight = ((triviaMenuHeight * 3 / 4) - (triviaMenuHeight * 7 / 30));
        double answerHitboxHeight = answerSelectionHeight / 4;
        double mouseX;
        double mouseY;
        // Update mouse position
        mouseX = MouseInfo.getPointerInfo().getLocation().getX();
        mouseY = MouseInfo.getPointerInfo().getLocation().getY();
        mousePos = new int[] {(int)mouseX, (int)mouseY};
        //Constantly fade out action text
        for(int i = 0; i < actionTextFades.length; i++){
            if(actionTextFades[i] > 30){
                actionTextFades[i] -= 1;
            }
        }
        testCounter++;
        this.repaint();

        if(inTriviaMenu){
            // TRIVIA UI UPDATING
            

            
                // Is the mouse hovering over an answer?
                if(mouseX > triviaMenuX && 
                mouseX < triviaMenuX + triviaMenuWidth &&
                mouseY > triviaMenuY + triviaMenuHeight / 4 + (answerHitboxHeight / 2)
                && mouseY < triviaMenuY + (triviaMenuHeight * 23 / 30 ) + (answerHitboxHeight / 2)){
                
                // Do some math to figure out which answer specifically, 0-3.
                int answerHovered = (int)((mouseY - triviaMenuY - (triviaMenuHeight / 4) - (answerHitboxHeight / 2)) / answerHitboxHeight);
                selectRectPos = answerHovered;
                } else {
                    selectRectPos = -1;
                }

            
        }
        if(moveAnimStart != -1 && now - moveAnimStart <= 1500){
            t = (double)((double)(now - moveAnimStart) / (double)500);
            d = D/2 * ((double)1 - (double)Math.cos((Math.PI / 3) * t));
            mapOffset = new double[] {lastOffset[0] + d * distanceMovingTo[0], lastOffset[1] + d * distanceMovingTo[1]};
            System.out.println(t);
        } else if(moveAnimStart != -1 && now - moveAnimStart > 1500){
            disableClicks = false;
        } else {
            moveAnimStart = -1;
        }
    }
}
