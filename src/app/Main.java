package app;

import java.io.*;
import app.DiaryApplication;

public class Main {
    public static String congfig[] = {"player 1", "player 2", "0", "0"};
    public static String names[] = {congfig[0], congfig[1]};

    public static void saveConfigToFile() {
        try (FileWriter writer = new FileWriter("config.txt")) {
            for (String value : congfig) {
                writer.write(value + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving config: " + e.getMessage());
        }
    }

    public static void loadConfigFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("config.txt"))) {
            for (int i = 0; i < congfig.length; i++) {
                String line = reader.readLine();
                if (line != null) congfig[i] = line;
            }
            names[0] = congfig[0];
            names[1] = congfig[1];
        } catch (IOException e) {
            System.out.println("No config file found. Using default settings.");
        }
    }

    public static void saveDataToFile(String data) {
        try (FileWriter writer = new FileWriter("data.txt")) {
            writer.write(data + "\n");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        DiaryApplication.run();
    }

}
