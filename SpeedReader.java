import java.util.ArrayList;
import processing.core.*;

public class SpeedReader extends PApplet {
    private ArrayList<String> words;
    private PFont font;
    private int currentWordIndex;
    private int wordDisplayDuration;
    private int previousMillis;
    private boolean isPaused;

    public void settings() {
        size(800, 600);
    }

    public void setup() {
        // Read in the text file
        words = new ArrayList<String>();
        try {
            String[] lines = loadStrings("text.txt");
            for (String line : lines) {
                String[] wordsInLine = line.split(" ");
                for (String word : wordsInLine) {
                    // Remove any leading/trailing punctuation from each word
                    word = word.replaceAll("^\\W+|\\W+$", "");
                    words.add(word);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Set up font
        font = createFont("Helvetica", 36);

        // Set up word display settings
        currentWordIndex = 0;
        wordDisplayDuration = 60000 / 120; // default speed is 120 wpm
        previousMillis = millis();
        isPaused = false;
    }

    public void draw() {
        // Display the current word
        background(255);
        fill(0);
        textAlign(CENTER, CENTER);
        textFont(font);
        text(words.get(currentWordIndex), width/2, height/2);

        // Update the word display
        if (!isPaused && millis() - previousMillis > wordDisplayDuration) {
            currentWordIndex++;
            if (currentWordIndex >= words.size()) {
                noLoop();
            } else {
                wordDisplayDuration = 60000 / speedSliderValue();
                previousMillis = millis();
            }
        }
    }

    public void keyPressed() {
        if (key == ' ') {
            isPaused = !isPaused;
        }
    }

    private int speedSliderValue() {
        // Returns the value of the speed slider, which is mapped from 120-500 to 1-100
        return map(constrain(mouseX, speedSliderX(), speedSliderX() + speedSliderWidth()), speedSliderX(), speedSliderX() + speedSliderWidth(), 1, 100) * 4 + 120;
    }

    private int speedSliderX() {
        // Returns the x-coordinate of the left edge of the speed slider
        return width/2 - 200;
    }

    private int speedSliderWidth() {
        // Returns the width of the speed slider
        return 400;
    }

    public static void main(String[] args) {
        PApplet.main("SpeedReader");
    }
}
