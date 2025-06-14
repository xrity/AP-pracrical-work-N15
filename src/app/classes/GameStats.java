package app.classes;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;

public class GameStats {
    public String winnerName;
    public char symbol;
    public int steps;
    public LocalTime time;

    public GameStats(String winnerName, char symbol, int steps, LocalTime time) {
        this.winnerName = winnerName;
        this.symbol = symbol;
        this.steps = steps;
        this.time = time;
    }

    public void saveToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(winnerName + " wins by " + symbol + " "
                    + steps + " steps " + time + "\n");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
}
