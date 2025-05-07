import java.util.Random;
import java.util.Scanner;
import java.time.LocalTime;
import java.io.*;

public class Main {
    public static Scanner sc = new Scanner(System.in);
    public static Random rand = new Random();
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

    public static char[][] initialMap(int map_size) {
        char[][] map = new char[map_size][map_size];

        //initial
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = ' ';
            }
        }

        //nuber gen
        for (int i = 0; i < map.length; i++) {
            if (i % 2 == 0) {
                map[i][0] = Integer.toString(i / 2).charAt(0);
                map[0][i] = Integer.toString(i / 2).charAt(0);
            }
        }

        //border gen
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (i % 2 != 0) {
                    if (j % 2 != 0) {
                        map[i][j] = '+';
                    } else {
                        map[i][j] = '-';
                    }
                } else if (j % 2 != 0) {
                    map[i][j] = '|';
                }
            }
        }
        return map;
    }

    public static void renderMap(char[][] map) {
        System.out.println("\n");
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + "  ");
            }
            System.out.print("\n");
        }
    }

    public static char[][] makeAStep(int playerTurn, char[] players, char[][] map) {
        System.out.println("\nits " + names[playerTurn % players.length] + " turn");
        int[] usercoords = new int[2];
        while (true) {
            if(playerTurn % players.length != 0 || congfig[3].equals("0")){
                //x
                System.out.println("write coords (x) (0 - exit)");
                usercoords[0] = inputValidate()*2;
                if (usercoords[0] == 0){
                    return null;
                }
                //y
                System.out.println("write coords (y) (0 - exit)");
                usercoords[1] = inputValidate()*2;
                if (usercoords[1] == 0){
                    return null;
                }
                //valid process
                if (usercoords[1] > map.length || usercoords[0] > map.length){
                    System.out.println("out of bounds, redo");
                }
                else if (map[usercoords[1]][usercoords[0]] != ' ') {
                    System.out.println("not allowed, redo");
                }
                else {
                    break;
                }
            }
            else {
                usercoords[0] = (rand.nextInt(map.length) + 1) * 2;
                usercoords[1] = (rand.nextInt(map.length) + 1) * 2;

                if (usercoords[0] >= map.length || usercoords[1] >= map.length) {
                    continue;
                }
                if (map[usercoords[1]][usercoords[0]] == ' ') {
                    break;
                }
            }
        }

        map[usercoords[1]][usercoords[0]] = players[playerTurn % players.length];
        return map;
    }

    public static boolean winProcess(char player, char[][] map) {
        int winLength = 3;

        for (int i = 2; i < map.length; i += 2) {
            for (int j = 2; j < map.length - 2 * (winLength - 1); j += 2) {
                if (j + 2 * (winLength - 1) < map.length && map[i][j] == player
                        && map[i][j + 2] == player && map[i][j + 4] == player) {
                    return true;
                }
                if (i + 2 * (winLength - 1) < map.length && map[j][i] == player
                        && map[j + 2][i] == player && map[j + 4][i] == player) {
                    return true;
                }
            }
        }

        for (int i = 2; i < map.length - 2 * (winLength - 1); i += 2) {
            for (int j = 2; j < map.length - 2 * (winLength - 1); j += 2) {
                if (map[i][j] == player && map[i + 2][j + 2] == player
                        && map[i + 4][j + 4] == player) {
                    return true;
                }
                if (map[i][j + 4] == player && map[i + 2][j + 2] == player
                        && map[i + 4][j] == player) {
                    return true;
                }
            }
        }
        return false;

    }

    public static int inputValidate(){
        while (!sc.hasNextInt()) {
            sc.nextLine();
            System.out.println("redo");
        }
        int choice = sc.nextInt();
        sc.nextLine();
        return choice;
    }

    public static int settings(int size_index){
        System.out.println("\nSettings");
        System.out.println("1. Rename");
        System.out.println("2. Resize");
        System.out.println("3. Mode");

        int choice = inputValidate();


        if(choice == 1){
            System.out.println("\n1 Player name");
            names[0] = sc.nextLine();
            congfig[0] = names[0];
            System.out.println("2 Player name");
            names[1] = sc.nextLine();
            congfig[1] = names[1];

        } else if(choice == 2){
            System.out.println("\nEnter map size (0 to exit)");
            System.out.println("1. 3x3");
            System.out.println("2. 5x5");
            System.out.println("3. 7x7");
            System.out.println("4. 9x9");

            int input = inputValidate();
            if (input == 0) return size_index;
            size_index = input - 1;
            congfig[2] = Integer.toString(size_index);

        } else if(choice == 3){
            System.out.println("\nTwo player mode");
            System.out.println("1. Yes");
            System.out.println("2. No");
            congfig[3] = Integer.toString(inputValidate() - 1);
        }


        saveConfigToFile();
        return size_index;



    }

    public static void main(String[] args) {
        loadConfigFromFile();
        int size_index = Integer.parseInt(congfig[2]);

        while(true) {
            System.out.println("Welcome to the game of TicTac Game!");
            System.out.println("\n1. Play");
            System.out.println("2. Settings");
            System.out.println("0. Exit");

            int choice;
            choice = inputValidate();

            switch (choice) {
                case 1:
                    System.out.println("\nStarting game with " + size_index + " size");

                    int[] sizes = {3, 5, 7, 9};
                    int size = sizes[size_index];
                    int map_size = (size + 1) * 2;
                    char[][] map = initialMap(map_size);

                    char[] players = {'x', 'o'};
                    int playerTurn = -1;

                    while (true) {
                        playerTurn++;
                        renderMap(map);

                        map = makeAStep(playerTurn, players, map);
                        if(map == null){
                            System.out.println("Bye :3");
                            break;
                        }
                        renderMap(map);

                        char player = players[playerTurn % players.length];
                        String name = names[playerTurn % players.length];

                        if(winProcess(player, map)){
                            renderMap(map);
                            System.out.println(name + " wins by " + player);
                            System.out.println(playerTurn+1 + " steps");
                            LocalTime time = LocalTime.now();
                            System.out.println(time + " time");
                            String data = name + " wins by " + player + " "
                                    + playerTurn+1 + " steps" + " "
                                    + time + " time";
                            saveDataToFile(data);
                            break;
                        };
                    }
                    break;


                case 2:
                    size_index = settings(size_index);
                    break;

                case 0:
                    System.out.println("\n1. Back");
                    System.out.println("2. Agree");

                    choice = inputValidate();

                    if (choice == 1) break;
                    else if (choice == 2) System.exit(0);


                default: break;
            }
        }
    }
}
