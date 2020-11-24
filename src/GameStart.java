public class GameStart {

    public static final char USER_CHAR = 'X';
    public static final char COMP_CHAR = 'O';
    public static final char EMPTY_CHAR = '_';
    public static final int INDEX_X = 1;
    public static final int INDEX_Y = 0;
    private static char[][] cells;
    private static Computer computer;
    private static RowChecker rowChecker;

    GameStart(int fieldSize, WinScreen winScreen, int gameMode, int gameWinCount, GameMap gameMap) {
        fieldInit(fieldSize);
        rowChecker = new RowChecker(winScreen, fieldSize, cells, gameWinCount);
        computer = new Computer(gameMode, fieldSize, gameWinCount, gameMap, rowChecker, cells);
    }

    private void fieldInit(int fieldSize) {
        cells = new char[fieldSize][fieldSize];
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                cells[i][j] = EMPTY_CHAR;
            }
        }
    }

    public static void buttonPressed(int y, int x) throws InterruptedException {
        cells[y][x] = USER_CHAR;
        rowChecker.checkRaw(USER_CHAR);
        int[] compMove = computer.makeMove();
        cells[compMove[INDEX_Y]][compMove[INDEX_X]] = COMP_CHAR;
        rowChecker.checkRaw(COMP_CHAR);
    }
}
