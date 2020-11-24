import java.util.Map;
import java.util.Random;

public class Computer {

    private static final Random RANDOM = new Random();
    private static final int INDEX_X = GameStart.INDEX_X;
    private static final int INDEX_Y = GameStart.INDEX_Y;

    public static final int MODE_EASY = 1;
    public static final int MODE_NORMAL = 2;
    public static final int MODE_HARD = 3;

    private final int gameMode;
    private final int fieldSize;
    private final int gameWinCount;
    private final char[][] cells;
    private int userMaxIndex;

    private final GameMap gameMap;
    private final RowChecker rowChecker;

    private int[] compPositionToMove = new int[2];
    private int[] userBestPosition = new int[2];

    Computer(int gameMode, int fieldSize, int gameWinCount, GameMap gameMap, RowChecker rowChecker, char[][] cells) {
        this.fieldSize = fieldSize;
        this.gameWinCount = gameWinCount;
        this.gameMap = gameMap;
        this.rowChecker = rowChecker;
        this.cells = cells;
        this.gameMode = gameMode;
    }

    public int[] makeMove() {
        userMaxIndex = 0;
        switch (gameMode) {
            case MODE_EASY:
                makeEasyMove();
                break;
            case MODE_NORMAL:
                makeNormalMove();
                break;
            case MODE_HARD:
                makeNormalMove();
                makeHardMove();
                break;
            default:
                throw new RuntimeException("Unexpected comp difficulty. Game mode: " + gameMode);
        }
        gameMap.buttonLowered(compPositionToMove[INDEX_Y], compPositionToMove[INDEX_X], true);
        return compPositionToMove;
    }

    private void makeEasyMove() {
        do {
            compPositionToMove[INDEX_Y] = RANDOM.nextInt(fieldSize);
            compPositionToMove[INDEX_X] = RANDOM.nextInt(fieldSize);
        } while (isUsedCell(compPositionToMove[INDEX_Y], compPositionToMove[INDEX_X]));
    }

    private void makeNormalMove() {
        userMaxIndex = getNormalAndHardMoves(rowChecker.getUserMoves(), false);
    }

    private void makeHardMove() {
        int compMaxIndex = getNormalAndHardMoves(rowChecker.getCompMoves(), true);
        if (compMaxIndex < gameWinCount) {
            if (userMaxIndex >= compMaxIndex) {
                compPositionToMove[INDEX_Y] = userBestPosition[INDEX_Y];
                compPositionToMove[INDEX_X] = userBestPosition[INDEX_X];
            }
        }
        putCenterAt3x3();
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

    private int getNormalAndHardMoves(Map<Integer, int[]> moves, boolean hardMode) {
        for (int i = fieldSize * 2; i >= 0; i--) {
            if (moves.containsKey(i)) {
                if (hardMode) compPositionToMove = moves.get(i);
                else userBestPosition = moves.get(i);
                return i;
            }
        }
        return 0;
    }
}
