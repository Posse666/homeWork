import java.util.Map;
import java.util.Random;

public class Computer {

    private static final Random RANDOM = new Random();
    private static final int INDEX_X = GameStart.INDEX_X;
    private static final int INDEX_Y = GameStart.INDEX_Y;

    private boolean easy;
    private boolean normal;
    private boolean hard;

    private final int fieldSize;
    private final int gameWinCount;
    private final char[][] cells;

    private final GameMap gameMap;
    private final RowChecker rowChecker;

    private int[] compPositionToMove = new int[2];
    private int[] userBestPosition = new int[2];

    Computer(int gameMode, int fieldSize, int gameWinCount, GameMap gameMap, RowChecker rowChecker, char[][] cells) {
        switch (gameMode) {
            case 1:
                easy = true;
                break;
            case 3:
                hard = true;
            case 2:
                normal = true;
                break;
            default:
                throw new RuntimeException("Unexpected comp difficulty. Game mode: " + gameMode);
        }
        this.fieldSize = fieldSize;
        this.gameWinCount = gameWinCount;
        this.gameMap = gameMap;
        this.rowChecker = rowChecker;
        this.cells = cells;
    }

    public int[] makeMove() {
        int userMaxIndex = 0;

        if (easy) {
            do {
                compPositionToMove[INDEX_Y] = RANDOM.nextInt(fieldSize);
                compPositionToMove[INDEX_X] = RANDOM.nextInt(fieldSize);
            } while (isUsedCell(compPositionToMove[INDEX_Y], compPositionToMove[INDEX_X]));
        }
        if (normal) {
            userMaxIndex = getNormalAndHardMoves(rowChecker.getUserMoves(), true);
        }
        if (hard) {
            int compMaxIndex = getNormalAndHardMoves(rowChecker.getCompMoves(), false);
            if (compMaxIndex < gameWinCount) {
                if (userMaxIndex >= compMaxIndex) {
                    compPositionToMove[INDEX_Y] = userBestPosition[INDEX_Y];
                    compPositionToMove[INDEX_X] = userBestPosition[INDEX_X];
                }
            }
            putCenterAt3x3();
        }
        gameMap.buttonLowered(compPositionToMove[INDEX_Y], compPositionToMove[INDEX_X], true);
        return new int[]{compPositionToMove[INDEX_Y], compPositionToMove[INDEX_X]};
    }

    private void putCenterAt3x3() {
        if (fieldSize == 3 && cells[1][1] == GameStart.EMPTY_CHAR) {
            compPositionToMove[INDEX_Y] = 1;
            compPositionToMove[INDEX_X] = 1;
        }
    }

    private boolean isUsedCell(int y, int x) {
        return cells[y][x] != GameStart.EMPTY_CHAR;
    }

    private int getNormalAndHardMoves(Map<Integer, int[]> moves, boolean user) {
        for (int i = fieldSize * 2; i >= 0; i--) {
            if (moves.containsKey(i)) {
                if (user) userBestPosition = moves.get(i);
                else compPositionToMove = moves.get(i);
                return i;
            }
        }
        return 0;
    }
}
