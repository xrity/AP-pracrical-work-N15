package app.classes;

import app.classes.GameConfig;

public class GameField {
    public char[][] map;
    public int size;
    public char cross;
    public char vertical;
    public char horizontal;

    public GameField() {
        this.size = 8;
        this.map = new char[size][size];
        this.cross = '+';
        this.vertical = '|';
        this.horizontal = '-';

    }

    public void changeSize(int inputSize){
        size = (inputSize+1)*2;
        map = new char[size][size];
    }

    public char[][] initialMap() {
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
                        map[i][j] = cross;
                    } else {
                        map[i][j] = horizontal;
                    }
                } else if (j % 2 != 0) {
                    map[i][j] = vertical;
                }
            }
        }
        return map;
    }

    public void renderMap() {
        System.out.println("\n");
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + "  ");
            }
            System.out.print("\n");
        }
    }

}
