package app.classes;

import java.io.*;

public class GameConfig {
    public static String filename = "config.txt";

    public String player1;
    public String player2;
    public int size;
    public boolean isMultiplayer;

    public GameConfig() {
        this.player1 = "player 1";
        this.player2 = "player 2";
        this.size = 3;
        this.isMultiplayer = true;
    }

    public void saveToFile() {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(player1 + "\n");
            writer.write(player2 + "\n");
            writer.write(size + "\n");
            writer.write((isMultiplayer ? "1" : "0") + "\n");
        } catch (IOException e) {
            System.out.println("Error saving config: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            player1 = reader.readLine();
            player2 = reader.readLine();
            size = Integer.parseInt(reader.readLine());
            isMultiplayer = reader.readLine().equals("1");
        } catch (IOException e) {
            System.out.println("No config file found. Using default settings.");
        }
    }
}
