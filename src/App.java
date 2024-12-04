import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import processing.core.PApplet;

public class App extends PApplet {
    double timer;
    int scene;
    double highScore;
    double gameStart;
    ArrayList<Bubble> bubbles;

    public static void main(String[] args) {
        PApplet.main("App");
    }

    public void setup() {
        try (Scanner scanner = new Scanner(Paths.get("file.txt"))) {

            // we read the file until all lines have been read
            while (scanner.hasNextLine()) {
                // we read one line
                String row = scanner.nextLine();
                // we print the line that we read
                System.out.println(row);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        bubbles = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            bubbleMaker();
        }
        scene = 0;
        gameStart = millis();

    }

    public void settings() {
        size(800, 800);

    }

    public void draw() {
        background(0);

        if (scene == 0) {
            for (Bubble b : bubbles) {
                b.display();
                b.update();
            }
            fill(255);
            textSize(50);
            timer = millis() - gameStart;
            timer = ((int) timer / 100) / 10.0;
            text("" + timer, width - 100, 100);
            if (bubbles.size() == 0) {
                scene = 1;
                readHighScore();
                


                if (highScore == 0 || highScore > timer) {
                    highScore = timer;
                    saveHighScore();
                }
            }
           
        } else {
            text("Score:" + timer, 400, 400);
            text("High Score:" + highScore, 400, 600);

        }

    }
     public void saveHighScore(){
        int numberToSave = 123; // This is the integer we want to save
        String filePath = "output.txt"; // Path to the text file

        try (PrintWriter writer = new PrintWriter("highscore.txt")){
            writer.println(highScore); // Writes the integer to the file
            writer.close(); // Closes the writer and saves the file
            System.out.println("Integer saved to file successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }

     }

    public void keyPressed() {
        if (key == ' ') {
            if (scene == 0) {
                bubbles.clear();
            } else {
                setup();
            }

            // for(int i = 0; i < 100; i++){

            // }

        }
    }
    public void readHighScore(){
        try (Scanner scanner = new Scanner(Paths.get("hihscore.txt"))) {

            // we read the file until all lines have been read
            while (scanner.hasNextLine()) {
                // we read one line
                String row = scanner.nextLine();
                // we print the line that we read
                System.out.println(row);
                highScore =  Double.valueOf(row);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        
    }

    public void bubbleMaker() {
        int x = (int) random(1000);
        int y = (int) random(800);

        Bubble bubble = new Bubble(y, x, this);
        bubbles.add(bubble);
    }

    public void mousePressed() {
        for (int i = 0; i < bubbles.size(); i++) {
            Bubble b = bubbles.get(i);
            if (b.checkTouch(mouseX, mouseY) == false) {
                bubbles.remove(b);
            }

        }
    }
}
