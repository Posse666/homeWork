import java.util.*;

public class RowChecker {

    private static final Random RANDOM = new Random();
    private final Map<Integer, int[]> compMoves = new HashMap<>();
    private final Map<Integer, int[]> userMoves = new HashMap<>();
    private final int[] userPrevPos = new int[2];
    private final int[] compPrevPos = new int[2];
    private final int[] prevPos = new int[2];
    private static final int ILLEGAL_ARRAY_ARGUMENT = -1;
    private static final char USER_CHAR = GameStart.USER_CHAR;
    private static final char COMP_CHAR = GameStart.COMP_CHAR;
    private static final char EMPTY_CHAR = GameStart.EMPTY_CHAR;
    private static final int INDEX_X = GameStart.INDEX_X;
    private static final int INDEX_Y = GameStart.INDEX_Y;
    private final int WIN_SITUATION_INDEX;
    private final int[] userLastAvailablePos = new int[2];
    private final int[] compLastAvailablePos = new int[2];
    private int userRow;
    private boolean draw;
    private int maxPossibleUserRow;
    private int lastAvailableUserRow;
    private int compRow;
    private int maxPossibleCompRow;
    private int lastAvailableCompRaw;
    private final int fieldSize;
    private int emptyCells;
    private final int gameWinCount;
    private int userChars;
    private int compChars;
    private boolean lastMove;
    private boolean possibleFullRow;
    private boolean nonEmptyCharFound;
    private final char[][] cells;
    private char currentChar;
    private final WinScreen winScreen;

    RowChecker(WinScreen winScreen, int fieldSize, char[][] cells, int gameWinCount) {
        this.winScreen = winScreen;
        this.fieldSize = fieldSize;
        this.cells = cells;
        this.gameWinCount = gameWinCount;
        this.WIN_SITUATION_INDEX = fieldSize * 2;
    }

    public void checkRaw(char currentChar) throws InterruptedException {
        this.currentChar = currentChar;
        compMoves.clear();
        userMoves.clear();
        draw = true;
        ArrayList<Integer> randomCheck = getRandomNumbers();
        for (Integer randomNumber : randomCheck) {
            boolean diagonal = false;
            boolean revDiagonal = false;
            boolean horizontal = false;
            boolean vertical = false;
            switch (randomNumber) {
                case 1:
                    diagonal = true;
                    break;
                case 2:
                    revDiagonal = true;
                    break;
                case 3:
                    horizontal = true;
                    break;
                case 4:
                    vertical = true;
            }
            rowChecker(diagonal, revDiagonal, horizontal, vertical);
        }
        if (draw) {
            winScreen.showScreen(EMPTY_CHAR);
            throw (new InterruptedException());
        }
    }

    private void rowChecker(boolean diagonal, boolean revDiagonal, boolean horizontal, boolean vertical) throws InterruptedException {
        int iBounds = 1;
        if (diagonal || revDiagonal) iBounds = fieldSize - gameWinCount + 1;
        for (int i = 0; i < iBounds; i++) {
            int jBounds = fieldSize;
            if (diagonal || revDiagonal) jBounds = fieldSize - gameWinCount + 1;
            for (int j = 0; j < jBounds; j++) {
                int min = fieldSize;
                if (diagonal || revDiagonal) {
                    elementsToStartValues(i, j);
                    min = fieldSize - i;
                    if (min > fieldSize - j) min = fieldSize - j;
                } else if (horizontal) elementsToStartValues(j, 0);
                else elementsToStartValues(0, j);
                for (int k = 0; k < min; k++) {
                    if (diagonal) getBestMove(i + k, j + k);
                    if (revDiagonal) getBestMove(Math.abs(i + k - (fieldSize - 1)), j + k);
                    if (horizontal) getBestMove(j, k);
                    if (vertical) getBestMove(k, j);
                }
                endOfBounds();
            }
        }
    }

    private void getBestMove(int y, int x) throws InterruptedException {
        checkEmptyChar(y, x);
        checkCurrentChar(y, x, USER_CHAR, COMP_CHAR, userPrevPos, compPrevPos, compMoves);
        checkCurrentChar(y, x, COMP_CHAR, USER_CHAR, compPrevPos, userPrevPos, userMoves);
        if (compRow >= gameWinCount || userRow >= gameWinCount) {
            winScreen.showScreen(currentChar);
            throw (new InterruptedException());
        }
        prevPos[INDEX_Y] = y;
        prevPos[INDEX_X] = x;
        if (maxPossibleCompRow >= gameWinCount || maxPossibleUserRow >= gameWinCount) draw = false;
    }

    private void checkEmptyChar(int y, int x) {
        if (cells[y][x] == EMPTY_CHAR) {
            maxPossibleCompRow++;
            maxPossibleUserRow++;
            emptyCells++;
            setLastAvailablePos(USER_CHAR, userLastAvailablePos, userRow, y, x, lastAvailableUserRow);
            setLastAvailablePos(COMP_CHAR, compLastAvailablePos, compRow, y, x, lastAvailableCompRaw);
            setPreviousAvailablePos(userPrevPos, y, x);
            setPreviousAvailablePos(compPrevPos, y, x);
            checkMatchedRow(userRow, userPrevPos, userMoves, userLastAvailablePos, maxPossibleUserRow, lastAvailableUserRow, userChars);
            checkMatchedRow(compRow, compPrevPos, compMoves, compLastAvailablePos, maxPossibleCompRow, lastAvailableCompRaw, compChars);
            userRow = 0;
            compRow = 0;
        }
    }

    private void setLastAvailablePos(char currentChar, int[] lastAvailablePos, int currentRow, int y, int x, int lastAvailableRaw) {
        if (cells[prevPos[INDEX_Y]][prevPos[INDEX_X]] == currentChar) {
            if (lastAvailableRaw == 0 && lastAvailableRaw < currentRow) {
                lastAvailablePos[INDEX_Y] = y;
                lastAvailablePos[INDEX_X] = x;
                if (currentChar == USER_CHAR) lastAvailableUserRow = currentRow;
                else lastAvailableCompRaw = currentRow;
            }
        }
    }

    private void setPreviousAvailablePos(int[] prevPos, int y, int x) {
        if ((prevPos[INDEX_Y] == ILLEGAL_ARRAY_ARGUMENT && prevPos[INDEX_X] == ILLEGAL_ARRAY_ARGUMENT) || (!nonEmptyCharFound)) {
            prevPos[INDEX_Y] = y;
            prevPos[INDEX_X] = x;
        }
    }

    private void checkMatchedRow(int currentRaw, int[] prevPos, Map<Integer, int[]> moves, int[] currentPos, int maxPossibleRow, int lastAvailableRow, int charsCount) {
        if (maxPossibleRow >= gameWinCount && (currentRaw > 0 || lastAvailableRow > 0)) {
            if (lastMove) moves.put(charsCount + 1, new int[]{currentPos[INDEX_Y], currentPos[INDEX_X]});
            boolean isGetPreviousPos = RANDOM.nextInt(2) > 0;
            if (isGetPreviousPos && prevPos[INDEX_Y] >= 0 && prevPos[INDEX_X] >= 0 && !possibleFullRow) {
                setPossibleMove(currentRaw, prevPos, moves, lastAvailableRow, prevPos, charsCount, currentPos);
            } else {
                setPossibleMove(currentRaw, currentPos, moves, lastAvailableRow, prevPos, charsCount, currentPos);
            }
        }
    }

    private void setPossibleMove(int currentRow, int[] position, Map<Integer, int[]> moves, int lastAvailableRow, int[] prevPos, int charsCount, int[] lastCharPosition) {
        moves.put(lastAvailableRow, new int[]{position[INDEX_Y], position[INDEX_X]});
        if (currentRow + 2 >= gameWinCount && prevPos[INDEX_Y] >= 0 && prevPos[INDEX_X] >= 0) {
            moves.put(currentRow + 1, new int[]{position[INDEX_Y], position[INDEX_X]});
        }
        if (lastMove && charsCount + 1 >= gameWinCount) {
            moves.put(WIN_SITUATION_INDEX, new int[]{lastCharPosition[INDEX_Y], lastCharPosition[INDEX_X]});
        }
        if (currentRow + 1 >= gameWinCount) {
            moves.put(WIN_SITUATION_INDEX, new int[]{position[INDEX_Y], position[INDEX_X]});
        }
    }

    private void checkCurrentChar(int y, int x, char currentChar, char enemyChar, int[] previousPos, int[] enemyPrevPos, Map<Integer, int[]> moves) {
        if (cells[y][x] == currentChar) {
            if (cells[prevPos[INDEX_Y]][prevPos[INDEX_X]] == EMPTY_CHAR) {
                previousPos[INDEX_Y] = prevPos[INDEX_Y];
                previousPos[INDEX_X] = prevPos[INDEX_X];
            }
            if (cells[prevPos[INDEX_Y]][prevPos[INDEX_X]] == enemyChar)
                Arrays.fill(previousPos, ILLEGAL_ARRAY_ARGUMENT);
            if (currentChar == USER_CHAR) {
                userRow++;
                maxPossibleUserRow++;
            } else {
                compRow++;
                maxPossibleCompRow++;
            }
            if (enemyPrevPos[INDEX_Y] >= 0 && enemyPrevPos[INDEX_X] >= 0)
                if (currentChar == USER_CHAR) {
                    if (maxPossibleCompRow >= gameWinCount) {
                        moves.put(compRow, new int[]{compPrevPos[INDEX_Y], compPrevPos[INDEX_X]});
                        if (lastMove)
                            moves.put(compChars + 1, new int[]{userPrevPos[INDEX_Y], userPrevPos[INDEX_X]});
                        if (compRow + 1 >= gameWinCount)
                            moves.put(WIN_SITUATION_INDEX, new int[]{compPrevPos[INDEX_Y], compPrevPos[INDEX_X]});
                    }
                } else if (maxPossibleUserRow >= gameWinCount) {
                    moves.put(userRow, new int[]{userPrevPos[INDEX_Y], userPrevPos[INDEX_X]});
                    if (lastMove)
                        moves.put(userChars + 1, new int[]{userPrevPos[INDEX_Y], userPrevPos[INDEX_X]});
                    if (userRow + 1 >= gameWinCount)
                        moves.put(WIN_SITUATION_INDEX, new int[]{userPrevPos[INDEX_Y], userPrevPos[INDEX_X]});
                }
            if (currentChar == USER_CHAR) {
                compRow = 0;
                maxPossibleCompRow = 0;
                userChars++;
                compChars = 0;
                lastAvailableCompRaw = 0;
            } else {
                userRow = 0;
                maxPossibleUserRow = 0;
                compChars++;
                userChars = 0;
                lastAvailableUserRow = 0;
            }
            if (!lastMove) lastMove = cells[prevPos[INDEX_Y]][prevPos[INDEX_X]] == EMPTY_CHAR && emptyCells <= 1;
            if (lastMove) possibleFullRow = true;
            emptyCells = 0;
            nonEmptyCharFound = true;
        }
    }

    private void endOfBounds() {
        checkBounds(userPrevPos, userMoves, userRow, maxPossibleUserRow, userChars);
        checkBounds(compPrevPos, compMoves, compRow, maxPossibleCompRow, compChars);
    }

    private void checkBounds(int[] prevPos, Map<Integer, int[]> moves, int currentRow, int maxPossibleRow, int charsCount) {
        if (prevPos[INDEX_Y] >= 0 && prevPos[INDEX_X] >= 0 && maxPossibleRow >= gameWinCount && currentRow > 0) {
            moves.put(currentRow, new int[]{prevPos[INDEX_Y], prevPos[INDEX_X]});
            if (lastMove) {
                moves.put(charsCount + 1, new int[]{prevPos[INDEX_Y], prevPos[INDEX_X]});
                if (charsCount + 1 >= gameWinCount)
                    moves.put(WIN_SITUATION_INDEX, new int[]{prevPos[INDEX_Y], prevPos[INDEX_X]});
            }
        }
    }

    private void elementsToStartValues(int y, int x) {
        userRow = 0;
        compRow = 0;
        userChars = 0;
        compChars = 0;
        maxPossibleUserRow = 0;
        maxPossibleCompRow = 0;
        lastAvailableUserRow = 0;
        lastAvailableCompRaw = 0;
        emptyCells = 0;
        lastMove = false;
        possibleFullRow = false;
        nonEmptyCharFound = false;
        Arrays.fill(userPrevPos, ILLEGAL_ARRAY_ARGUMENT);
        Arrays.fill(compPrevPos, ILLEGAL_ARRAY_ARGUMENT);
        Arrays.fill(userLastAvailablePos, ILLEGAL_ARRAY_ARGUMENT);
        Arrays.fill(compLastAvailablePos, ILLEGAL_ARRAY_ARGUMENT);
        prevPos[INDEX_Y] = y;
        prevPos[INDEX_X] = x;
    }

    private ArrayList<Integer> getRandomNumbers() {
        int neededRandomArraySize = 4;
        int randomNumber;
        ArrayList<Integer> numbersGenerated = new ArrayList<>();
        for (int i = 0; i < neededRandomArraySize; i++) {
            do {
                randomNumber = RANDOM.nextInt(neededRandomArraySize) + 1;
            } while (numbersGenerated.contains(randomNumber));
            numbersGenerated.add(randomNumber);
        }
        return numbersGenerated;
    }

    public Map<Integer, int[]> getCompMoves() {
        return compMoves;
    }

    public Map<Integer, int[]> getUserMoves() {
        return userMoves;
    }
}
