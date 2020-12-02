package model;

import controller.GameController;

import java.util.Map;
import java.util.Random;

public class Computer {

    private static final int MODE_VERY_EASY = 0;
    private static final int MODE_EASY = 1;
    private static final int MODE_NORMAL = 2;
    private static final int MODE_HARD = 3;

    private static final Random RANDOM = new Random();
    private final int INDEX_X;
    private final int INDEX_Y;

    private int gameMode;
    private int fieldSize;
    private int gameWinCount;
    private char[][] cells;
    private int userMaxIndex;
    private int compMaxIndex;
    private Map<Integer, int[]> compMoves;
    private Map<Integer, int[]> userMoves;

    private final GameController gameController;

    private int[] compPositionToMove = new int[2];
    private int[] userBestPosition = new int[2];

    public Computer(GameController gameController) {
        this.gameController = gameController;
        INDEX_X = gameController.getIndexX();
        INDEX_Y = gameController.getIndexY();
    }

    public void init(int gameMode, int fieldSize, int gameWinCount, char[][] cells) {
        this.fieldSize = fieldSize;
        this.gameWinCount = gameWinCount;
        this.cells = cells;
        this.gameMode = gameMode;
    }

    public int[] makeMove(Map<Integer, int[]> compMoves, Map<Integer, int[]> userMoves) {
        this.compMoves = compMoves;
        this.userMoves = userMoves;
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
        if (fieldSize == 3 && cells[1][1] == gameController.getEmptyChar()) {
            compPositionToMove[INDEX_Y] = 1;
            compPositionToMove[INDEX_X] = 1;
        }
    }

    private boolean isUsedCell(int y, int x) {
        return cells[y][x] != gameController.getEmptyChar();
    }

    private void getUserAndCompBestMoves() {
        for (int i = fieldSize * 2; i >= 0; i--) {
            if (compMaxIndex == 0 && compMoves.containsKey(i)) {
                compPositionToMove = compMoves.get(i);
                compMaxIndex = i;
            }
            if (userMaxIndex == 0 && userMoves.containsKey(i)) {
                userBestPosition = userMoves.get(i);
                userMaxIndex = i;
            }
            if (compMaxIndex != 0 && userMaxIndex != 0) return;
        }
    }

    public static int getModeVeryEasy() {
        return MODE_VERY_EASY;
    }

    public static int getModeEasy() {
        return MODE_EASY;
    }

    public static int getModeNormal() {
        return MODE_NORMAL;
    }

    public static int getModeHard() {
        return MODE_HARD;
    }
}
