package app;

import java.time.LocalTime;

import app.classes.GameStats;
import app.classes.GameField;
import app.utils.FilesystemUtilities;




public class DiaryApplication {

    public static GameField field = new GameField();

    public static void run() {

        while (true) {
            FilesystemUtilities.config.loadFromFile();
            String names[] = {FilesystemUtilities.config.player1, FilesystemUtilities.config.player2};
            FilesystemUtilities.names = names;
            System.out.println("Welcome to the game of TicTac Game!");
            System.out.println("\n1. Play");
            System.out.println("2. Settings");
            System.out.println("0. Exit");

            int choice;
            choice = FilesystemUtilities.inputValidate();

            switch (choice) {
                case 1:
                    System.out.println("\nStarting game with " + FilesystemUtilities.config.size + " size");
                    field.changeSize(FilesystemUtilities.config.size);
                    field.initialMap();

                    char[] players = {'x', 'o'};
                    int playerTurn = -1;

                    while (true) {
                        playerTurn++;
                        field.renderMap();

                        field.map = FilesystemUtilities.makeAStep(playerTurn, players, field.map);
                        if (field.map == null) {
                            System.out.println("Bye :3");
                            break;
                        }
                        field.renderMap();

                        char player = players[playerTurn % players.length];
                        String name = names[playerTurn % players.length];

                        if (FilesystemUtilities.winProcess(player, field.map)) {
                            field.renderMap();
                            System.out.println(name + " wins by " + player);
                            System.out.println(playerTurn + 1 + " steps");
                            LocalTime time = LocalTime.now();
                            System.out.println(time + " time");
                            new GameStats(name, player, playerTurn + 1, time);
                            break;
                        }
                        ;
                    }
                    break;


                case 2:
                    FilesystemUtilities.config.size = FilesystemUtilities.settings(FilesystemUtilities.config.size);
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



