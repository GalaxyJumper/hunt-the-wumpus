// The Main Class
// 06/07/2024
// Mr. Reiber AP CSA Periods 5 & 6

import java.awt.FontFormatException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws FontFormatException, IOException {
        System.setProperty("sun.java2d.uiScale", "0.7");
        new GameControl();
    }
}