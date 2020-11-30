package model;

import controller.GameController;

import java.util.Random;

public class Computer {

    public static final int MODE_VERY_EASY = 0;
    public static final int MODE_EASY = 1;
    public static final int MODE_NORMAL = 2;
    public static final int MODE_HARD = 3;

    private static final Random RANDOM = new Random();
    private static final int INDEX_X = GameConstants.INDEX_X;
    private static final int INDEX_Y = GameConstants.INDEX_Y;

    private final int gameMode;
    private final int fieldSize;
    private final int gameWinCount;
    private final char[][] cells;
    private int userMaxIndex;
    private int compMaxIndex;

    private final GameController gameController;

    private int[] compPositionToMove = new int[2];
    private int[] userBestPosition = new int[2];

    public Computer(int gameMode, int fieldSize, int gameWinCount, GameController gameController, char[][] cells) {
        this.fieldSize = fieldSize;
        this.gameWinCount = gameWinCount;
        this.gameController = gameController;
        this.cells = cells;
        this.gameMode = gameMode;
    }

    public int[] makeMove() {
        userMaxIndex = 0;
        compMaxIndex = 0;
        switch (gameMode) {
            case MODE_VERY_EASY:
                makeVeryEasyMove();
                break;
            case MODE_EASY:
                makeEasyMove();
                break;
            case MODE_NORMAL:
                makeNormalMove();
                break;
            case MODE_HARD:
                makeHardMove();
                break;
            default:
                throw new RuntimeException("Unexpected comp difficulty. Game mode: " + gameMode);
        }

        gameController.setComputerMove(compPositionToMove[INDEX_Y], compPositionToMove[INDEX_X]);
        return compPositionToMove;
    }

    private void makeVeryEasyMove() {
        do {
            compPositionToMove[INDEX_Y] = RANDOM.nextInt(fieldSize);
            compPositionToMove[INDEX_X] = RANDOM.nextInt(fieldSize);
        } while (isUsedCell(compPositionToMove[INDEX_Y], compPositionToMove[INDEX_X]));
    }

    private void makeEasyMove() {
        if (RANDOM.nextInt(3) > 1) makeVeryEasyMove();
        else if (RANDOM.nextInt(2) > 0) makeNormalMove();
        else makeHardMove();
    }

    private void makeNormalMove() {
        getUserAndCompBestMoves();
        if ((gameMode == MODE_NORMAL || gameMode == MODE_EASY) && userMaxIndex != 0) {
            compPositionToMove[INDEX_Y] = userBestPosition[INDEX_Y];
            compPositionToMove[INDEX_X] = userBestPosition[INDEX_X];
        }
    }

    private void makeHardMove() {
        makeNormalMove();
        if (compMaxIndex < gameWinCount) {
            if (userMaxIndex >= compMaxIndex) {
                compPositionToMove[INDEX_Y] = userBestPosition[INDEX_Y];
                compPositionToMove[INDEX_X] = userBestPosition[INDEX_X];
            }
        }
        putCenterAt3x3();
    }

    private void putCenterAt3x3() {
        if (fieldSize == 3 && cells[1][1] == GameConstants.EMPTY_CHAR) {
            compPositionToMove[INDEX_Y] = 1;
            compPositionToMove[INDEX_X] = 1;
        }
    }

    private boolean isUsedCell(int y, int x) {
        return cells[y][x] != GameConstants.EMPTY_CHAR;
    }

    private void getUserAndCompBestMoves() {
        for (int i = fieldSize * 2; i >= 0; i--) {
            if (compMaxIndex == 0 && gameController.getCompMoves().containsKey(i)) {
                compPositionToMove = gameController.getCompMoves().get(i);
                compMaxIndex = i;
            }
            if (userMaxIndex == 0 && gameController.getUserMoves().containsKey(i)) {
                userBestPosition = gameController.getUserMoves().get(i);
                userMaxIndex = i;
            }
        }
    }
}
