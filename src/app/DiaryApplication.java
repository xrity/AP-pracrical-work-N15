package app;

import java.time.LocalTime;
import app.utils.FilesystemUtilities;

public class DiaryApplication {

    public static String congfig[] = {"player 1", "player 2", "0", "0"};
    public static String names[] = {congfig[0], congfig[1]};

    public static void run() {
        Main.loadConfigFromFile();
        int size_index = Integer.parseInt(congfig[2]);

        while (true) {
            System.out.println("Welcome to the game of TicTac Game!");
            System.out.println("\n1. Play");
            System.out.println("2. Settings");
            System.out.println("0. Exit");

            int choice;
            choice = FilesystemUtilities.inputValidate();

            switch (choice) {
                case 1:
                    System.out.println("\nStarting game with " + size_index + " size");

                    int[] sizes = {3, 5, 7, 9};
                    int size = sizes[size_index];
                    int map_size = (size + 1) * 2;
                    char[][] map = FilesystemUtilities.initialMap(map_size);

                    char[] players = {'x', 'o'};
                    int playerTurn = -1;

                    while (true) {
                        playerTurn++;
                        FilesystemUtilities.renderMap(map);

                        map = FilesystemUtilities.makeAStep(playerTurn, players, map);
                        if (map == null) {
                            System.out.println("Bye :3");
                            break;
                        }
                        FilesystemUtilities.renderMap(map);

                        char player = players[playerTurn % players.length];
                        String name = names[playerTurn % players.length];

                        if (FilesystemUtilities.winProcess(player, map)) {
                            FilesystemUtilities.renderMap(map);
                            System.out.println(name + " wins by " + player);
                            System.out.println(playerTurn + 1 + " steps");
                            LocalTime time = LocalTime.now();
                            System.out.println(time + " time");
                            String data = name + " wins by " + player + " "
                                    + playerTurn + 1 + " steps" + " "
                                    + time + " time";
                            Main.saveDataToFile(data);
                            break;
                        }
                        ;
                    }
                    break;


                case 2:
                    size_index = FilesystemUtilities.settings(size_index);
                    break;

                case 0:
                    System.out.println("\n1. Back");
                    System.out.println("2. Agree");

                    choice = FilesystemUtilities.inputValidate();

                    if (choice == 1) break;
                    else if (choice == 2) System.exit(0);


                default:
                    break;
            }
        }
    }
}



