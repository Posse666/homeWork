package model;

public class GameConstants {
    public static final char FIRST_PLAYER_CHAR = 'X';
    public static final char SECOND_PLAYER_CHAR = 'O';
    public static final char EMPTY_CHAR = '_';
    public static final int INDEX_X = 1;
    public static final int INDEX_Y = 0;

    private char[][] cells;

    public void fieldInit(int fieldSize) {
        cells = new char[fieldSize][fieldSize];
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                cells[i][j] = EMPTY_CHAR;
            }
        }
    }

    public char[][] getCells() {
        return cells;
    }
}