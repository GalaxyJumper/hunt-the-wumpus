import java.awt.*;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.io.FileNotFoundException;
class Main {
  public static void main(String[] args) {

    Display screen = new Display(640, 360, "buh");
    screen.paint();
    String[] secrets = loadGameSecrets("GameSecrets.csv");
    System.out.println("////// Congrats your code works //////");
  }

  public static String[] loadGameSecrets(String filename) {
    ArrayList<String> secretsList = new ArrayList<>();

    try {
      Scanner fileScanner = new Scanner(new File(filename));

      while (fileScanner.hasNextLine()) {
        String line = fileScanner.nextLine();
        secretsList.add(line);
      }

      fileScanner.close();
    } catch (FileNotFoundException e) {
      System.err.println("File not found: " + filename);
    }

    // Convert ArrayList to String array
    String[] secretsArray = new String[secretsList.size()];
    secretsArray = secretsList.toArray(secretsArray);

    return secretsArray;
  }
}