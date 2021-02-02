import java.util.*;

public class Game {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();
    private static final Map<Integer, int[]> compMoves = new HashMap<>();
    private static final Map<Integer, int[]> userMoves = new HashMap<>();
    private static final int[] userPrevPos = new int[2];
    private static final int[] compPrevPos = new int[2];
    private static final int[] prevPos = new int[2];
    private static final int MAX_FIELD_SIZE = 9;
    private static final int MAX_WIN_COUNT = 5;
    private static final int ILLEGAL_ARRAY_ARGUMENT = -1;
    private static final char userChar = 'X';
    private static final char compChar = 'O';
    private static final char emptyChar = '_';
    private static final int INDEX_X = 1;
    private static final int INDEX_Y = 0;
    private static int[] userCurrentPos = new int[2];
    private static int[] compCurrentPos = new int[2];
    private static int userRaw;
    private static int compRaw;
    private static int fieldSize;
    private static int gameWinCount;
    private static char[][] cells;
    private static int x;
    private static int y;
    private static char currentChar = compChar;
    private static boolean easy;
    private static boolean normal;
    private static boolean hard;

    public static void main(String[] args) {
        initSettings();
        fieldInit();
        gameStart();
    }

    private static void initSettings() {
        do {
            System.out.print("Введите размер поля (3-" + MAX_FIELD_SIZE + "): ");
            fieldSize = getUserNumber();
        } while (fieldSize < 3 || fieldSize > MAX_FIELD_SIZE);
        int maxCount = MAX_WIN_COUNT;
        do {
            if (maxCount > fieldSize) maxCount = fieldSize;
            System.out.print("До сколько клеток в ряд будем играть? (3-" + maxCount + "): ");
            gameWinCount = getUserNumber();
        } while (gameWinCount < 3 || gameWinCount > maxCount);
        int difficulty;
        do {
            System.out.print("Введите сложность: 1 - Легко(Рандом), 2 - Нормально(Комп закрывает ходы), 3 - Тяжело(Еще и сам пытается выиграть): ");
            difficulty = getUserNumber();
            switch (difficulty) {
                case 1:
                    easy = true;
                    break;
                case 3:
                    hard = true;
                case 2:
                    normal = true;
            }
        } while (difficulty < 1 || difficulty > 3);
    }

    private static void fieldInit() {
        cells = new char[fieldSize][fieldSize];
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                cells[i][j] = emptyChar;
            }
        }
    }

    private static void gameStart() {
        for (int i = 0; i < Math.pow(fieldSize, 2); i++) {
            checkRaw();
            chooseWhomTurnNext();
            showField();
            makeMove();
        }
        currentChar = emptyChar;
        finalMessage();
    }

    private static void chooseWhomTurnNext() {
        if (currentChar == compChar) currentChar = userChar;
        else currentChar = compChar;
    }

    private static void makeMove() {
        if (currentChar == userChar) userTurn();
        else computerTurn();
        compMoves.clear();
        userMoves.clear();
        cells[y][x] = currentChar;
    }

    private static void userTurn() {
        int userX, userY;
        String errorInputMessage = "Необходимо вводить верные значения 1-" + fieldSize;

        do {
            System.out.println("Последовательно введите координаты хода:");
            userX = getUserCoordinates(errorInputMessage, "Введите координату Х: ");
            userY = getUserCoordinates(errorInputMessage, "Введите координату Y: ");
        } while (isUsedCell(userY, userX));
        y = userY;
        x = userX;
    }

    private static int getUserCoordinates(String errorInputMessage, String inputMessage) {
        boolean errorInput;
        int userInput;
        do {
            errorInput = false;
            System.out.print(inputMessage);
            userInput = getUserNumber() - 1;
            if (userInput < 0 || userInput >= fieldSize) {
                System.out.println(errorInputMessage);
                errorInput = true;
            }
        } while (errorInput);
        return userInput;
    }

    private static int getUserNumber() {
        return checkUserInput(SCANNER.nextLine());
    }

    private static int checkUserInput(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return ILLEGAL_ARRAY_ARGUMENT;
        }
    }

    private static boolean isUsedCell(int y, int x) {
        return cells[y][x] != emptyChar;
    }

    private static void computerTurn() {
        int compMaxIndex = 0;
        int userMaxIndex = 0;
        if (easy) {
            do {
                compPrevPos[INDEX_Y] = RANDOM.nextInt(fieldSize);
                compPrevPos[INDEX_X] = RANDOM.nextInt(fieldSize);
            } while (isUsedCell(compCurrentPos[INDEX_Y], compPrevPos[INDEX_X]));
        }
        if (normal) {
            for (int i = gameWinCount - 1; i >= 0; i--) {
                if (userMoves.containsKey(i)) {
                    userMaxIndex = i;
                    userCurrentPos = userMoves.get(i);
                    break;
                }
            }
        }
        if (hard) {
            for (int i = gameWinCount - 1; i >= 0; i--) {
                if (compMoves.containsKey(i)) {
                    compMaxIndex = i;
                    compCurrentPos = compMoves.get(i);
                    break;
                }
            }
            if (compMaxIndex + 1 < gameWinCount) {
                if (userMaxIndex > compMaxIndex) {
                    compCurrentPos[INDEX_Y] = userCurrentPos[INDEX_Y];
                    compCurrentPos[INDEX_X] = userCurrentPos[INDEX_X];
                }
            }
        }
        y = compCurrentPos[0];
        x = compCurrentPos[1];
        System.out.println("\nА я пойду: X - " + (x + 1) + ", Y - " + (y + 1) + "\n");
    }

    static void checkRaw() {
        int maxWaysToCheck = 4;
        int numberOfCheckedRows = 0;
        boolean check1completed = false;
        boolean check2completed = false;
        boolean check3completed = false;
        boolean check4completed = false;
        do {
            int randomNumber = RANDOM.nextInt(maxWaysToCheck) + 1;
            if (randomNumber == 1 && !check1completed) {
                checkDiagonal();
                check1completed = true;
                numberOfCheckedRows++;
            }
            if (randomNumber == 2 && !check2completed) {
                checkReversDiagonal();
                check2completed = true;
                numberOfCheckedRows++;
            }
            if (randomNumber == 3 && !check3completed) {
                checkHorizontal();
                check3completed = true;
                numberOfCheckedRows++;
            }
            if (randomNumber == 4 && !check4completed) {
                checkVertical();
                check4completed = true;
                numberOfCheckedRows++;
            }
        } while (numberOfCheckedRows < maxWaysToCheck);
    }

    private static void checkDiagonal() {
        for (int y = 0; y <= fieldSize - gameWinCount; y++) {
            for (int x = 0; x <= fieldSize - gameWinCount; x++) {
                elementsToStartValues(y, x);
                int min = fieldSize - y;
                if (min > fieldSize - x) min = fieldSize - x;
                for (int i = 0; i < min; i++) {
                    getBestMove(y + i, x + i);
                }
                endOfBounds();
            }
        }
    }

    private static void checkReversDiagonal() {
        for (int y = 0; y <= fieldSize - gameWinCount; y++) {
            for (int x = 0; x <= fieldSize - gameWinCount; x++) {
                elementsToStartValues(y, x);
                int min = fieldSize - y;
                if (min > fieldSize - x) min = fieldSize - x;
                for (int i = 0; i < min; i++) {
                    getBestMove(Math.abs(y + i - (fieldSize - 1)), x + i);
                }
                endOfBounds();
            }
        }
    }

    private static void checkHorizontal() {
        for (int y = 0; y < fieldSize; y++) {
            elementsToStartValues(y, 0);
            for (int x = 0; x < fieldSize; x++) {
                getBestMove(y, x);
            }
            endOfBounds();
        }
    }

    private static void checkVertical() {
        for (int x = 0; x < fieldSize; x++) {
            elementsToStartValues(0, x);
            for (int y = 0; y < fieldSize; y++) {
                getBestMove(y, x);
            }
            endOfBounds();
        }
    }

    private static void getBestMove(int y, int x) {
        if (cells[y][x] == emptyChar) {
            userCurrentPos[INDEX_Y] = y;
            userCurrentPos[INDEX_X] = x;
            compCurrentPos[INDEX_Y] = y;
            compCurrentPos[INDEX_X] = x;
            if (userRaw == 0) {
                userPrevPos[INDEX_Y] = y;
                userPrevPos[INDEX_X] = x;
            }
            if (compRaw == 0) {
                compPrevPos[INDEX_Y] = y;
                compPrevPos[INDEX_X] = x;
            }
            checkMatchedRow(userRaw, userPrevPos, userMoves, userCurrentPos);
            checkMatchedRow(compRaw, compPrevPos, compMoves, compCurrentPos);
            userRaw = 0;
            compRaw = 0;
        }
//        checkCurrentChar(y, x, userChar, compChar, userPrevPos, compPrevPos, compMoves);
//        checkCurrentChar(y, x, compChar, userChar, compPrevPos, userPrevPos, userMoves);
        if (cells[y][x] == userChar) {
            if (cells[prevPos[INDEX_Y]][prevPos[INDEX_X]] == compChar) Arrays.fill(userPrevPos, ILLEGAL_ARRAY_ARGUMENT);
            userRaw++;
            if (compPrevPos[INDEX_Y] >= 0 && compPrevPos[INDEX_X] >= 0)
                compMoves.put(compRaw, new int[]{compPrevPos[INDEX_Y], compPrevPos[INDEX_X]});
            compRaw = 0;
        }
        if (cells[y][x] == compChar) {
            if (cells[prevPos[INDEX_Y]][prevPos[INDEX_X]] == userChar) Arrays.fill(compPrevPos, ILLEGAL_ARRAY_ARGUMENT);
            compRaw++;
            if (userPrevPos[INDEX_Y] >= 0 && userPrevPos[INDEX_X] >= 0)
                userMoves.put(userRaw, new int[]{userPrevPos[INDEX_Y], userPrevPos[INDEX_X]});
            userRaw = 0;
        }
        if (compRaw >= gameWinCount || userRaw >= gameWinCount) finalMessage();
        prevPos[INDEX_Y] = y;
        prevPos[INDEX_X] = x;
    }

//    private static void checkCurrentChar(int y, int x, char currentChar, char enemyChar, int[] previousPos, int[] enemyPrevPos, Map<Integer, int[]> moves) {
//        if (cells[y][x] == currentChar) {
//            if (cells[prevPos[INDEX_Y]][prevPos[INDEX_X]] == enemyChar)
//                Arrays.fill(previousPos, ILLEGAL_ARRAY_ARGUMENT);
//            if (currentChar == userChar) userRaw++;
//            else compRaw++;
//            if (enemyPrevPos[INDEX_Y] >= 0 && enemyPrevPos[INDEX_X] >= 0)
//                moves.put(compRaw, new int[]{enemyPrevPos[INDEX_Y], enemyPrevPos[INDEX_X]});
//            if (currentChar == userChar) compRaw = 0;
//            else userRaw = 0;
//        }
//    }

    private static void checkMatchedRow(int currentRaw, int[] prevPos, Map<Integer, int[]> moves, int[] currentPos) {
        if (currentRaw > 0) {
            if (RANDOM.nextInt(3) > 0 && prevPos[INDEX_Y] >= 0 && prevPos[INDEX_X] >= 0)
                moves.put(currentRaw, new int[]{prevPos[INDEX_Y], prevPos[INDEX_X]});
            else moves.put(currentRaw, new int[]{currentPos[INDEX_Y], currentPos[INDEX_X]});
        }
    }

    private static void endOfBounds() {
        if (userPrevPos[INDEX_Y] >= 0 && userPrevPos[INDEX_X] >= 0 && userRaw > 0)
            userMoves.put(userRaw, new int[]{userPrevPos[INDEX_Y], userPrevPos[INDEX_X]});
        if (compPrevPos[INDEX_Y] >= 0 && compPrevPos[INDEX_X] >= 0 && compRaw > 0)
            compMoves.put(compRaw, new int[]{compPrevPos[INDEX_Y], compPrevPos[INDEX_X]});
    }

    private static void elementsToStartValues(int y, int x) {
        userRaw = 0;
        compRaw = 0;
        Arrays.fill(userPrevPos, ILLEGAL_ARRAY_ARGUMENT);
        Arrays.fill(compPrevPos, ILLEGAL_ARRAY_ARGUMENT);
        prevPos[INDEX_Y] = y;
        prevPos[INDEX_X] = x;
    }

    private static void finalMessage() {
        showField();
        if (currentChar == userChar) {
            System.out.println("Поздравляю, Вы победили!!!");
            System.exit(0);
        }
        if (currentChar == compChar) {
            System.out.println("Победил компьютер");
            System.exit(0);
        }
        System.out.println("Ничья");
        System.exit(0);
    }

    private static void showField() {
        System.out.println();
        System.out.print(" ");
        for (int i = 0; i < fieldSize; i++) {
            System.out.print(" " + (i + 1));
        }
        System.out.print(" => X");
        System.out.println();
        for (int y = 0; y < fieldSize; y++) {
            System.out.print((y + 1) + "|");
            for (int x = 0; x < fieldSize; x++) {
                System.out.print(cells[y][x] + "|");
            }
            System.out.println();
        }
        System.out.println("V");
        System.out.println("Y");
    }
}