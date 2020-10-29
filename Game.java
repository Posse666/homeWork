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
    private static int[] userCurrentPos = new int[2];
    private static int[] compCurrentPos = new int[2];
    private static int userRaw;
    private static int compRaw;
    private static int fieldSize;
    private static int gameWinCount;
    private static Chars[][] cells;
    private static int x;
    private static int y;
    private static Chars currentChar = Chars.O;
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
        cells = new Chars[fieldSize][fieldSize];
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                cells[i][j] = Chars._;
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
        currentChar = Chars._;
        finalMessage();
    }

    private static void chooseWhomTurnNext() {
        if (currentChar == Chars.O) currentChar = Chars.X;
        else currentChar = Chars.O;
    }

    private static void makeMove() {
        if (currentChar == Chars.X) userTurn();
        else computerTurn();
        compMoves.clear();
        userMoves.clear();
        cells[y][x] = currentChar;
    }

    private static void userTurn() {
        int userX, userY;
        boolean errorInput;
        String errorInputMessage = "Необходимо вводить верные значения 1-" + fieldSize;

        do {
            System.out.println("Последовательно введите координаты хода:");
            do {
                errorInput = false;
                System.out.print("Введите координату Х: ");
                userX = getUserNumber() - 1;
                if (userX < 0 || userX >= fieldSize) {
                    System.out.println(errorInputMessage);
                    errorInput = true;
                }
            } while (errorInput);
            do {
                errorInput = false;
                System.out.print("Введите координату Y: ");
                userY = getUserNumber() - 1;
                if (userY < 0 || userY >= fieldSize) {
                    System.out.println(errorInputMessage);
                    errorInput = true;
                }
            } while (errorInput);
        } while (isUsedCell(userY, userX));
        y = userY;
        x = userX;
    }

    private static int getUserNumber() {
        return checkUserInput(SCANNER.nextLine());
    }

    private static int checkUserInput(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static boolean isUsedCell(int y, int x) {
        return cells[y][x] != Chars._;
    }

    private static void computerTurn() {
        int compMaxIndex = 0;
        int userMaxIndex = 0;
        if (easy) {
            do {
                compPrevPos[0] = RANDOM.nextInt(fieldSize);
                compPrevPos[1] = RANDOM.nextInt(fieldSize);
            } while (isUsedCell(compCurrentPos[0], compPrevPos[1]));
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
            if (compMaxIndex - 1 != gameWinCount) {
                if (userMaxIndex > compMaxIndex) {
                    compCurrentPos[0] = userCurrentPos[0];
                    compCurrentPos[1] = userCurrentPos[1];
                }
            }
        }
        y = compCurrentPos[0];
        x = compCurrentPos[1];
        System.out.println("\nА я пойду: X - " + (x + 1) + ", Y - " + (y + 1) + "\n");
    }

    static void checkRaw() {
        ArrayList<Integer> randomCheck = getRandomNumbers();
        for (Integer randomNumber : randomCheck) {
            switch (randomNumber) {
                case 1:
                    checkDiagonal();
                    break;
                case 2:
                    checkReversDiagonal();
                    break;
                case 3:
                    checkHorizontal();
                    break;
                case 4:
                    checkVertical();
            }
        }
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
        if (cells[y][x] == Chars._) {
            userCurrentPos[0] = y;
            userCurrentPos[1] = x;
            compCurrentPos[0] = y;
            compCurrentPos[1] = x;
            if (userRaw == 0) {
                userPrevPos[0] = y;
                userPrevPos[1] = x;
            }
            if (compRaw == 0) {
                compPrevPos[0] = y;
                compPrevPos[1] = x;
            }
            if (userRaw > 0) {
                if (RANDOM.nextInt(3) > 0 && userPrevPos[0] >= 0 && userPrevPos[1] >= 0)
                    userMoves.put(userRaw, new int[]{userPrevPos[0], userPrevPos[1]});
                else userMoves.put(userRaw, new int[]{userCurrentPos[0], userCurrentPos[1]});
            }
            if (compRaw > 0) {
                if (RANDOM.nextInt(3) > 0 && compPrevPos[0] >= 0 && compPrevPos[1] >= 0)
                    compMoves.put(compRaw, new int[]{compPrevPos[0], compPrevPos[1]});
                else compMoves.put(compRaw, new int[]{compCurrentPos[0], compCurrentPos[1]});
            }
            userRaw = 0;
            compRaw = 0;
        }
        if (cells[y][x] == Chars.X) {
            if (cells[prevPos[0]][prevPos[1]] == Chars.O) {
                userPrevPos[0] = -1;
                userPrevPos[1] = -1;
            }
            userRaw++;
            if (compPrevPos[0] >= 0 && compPrevPos[1] >= 0)
                compMoves.put(compRaw, new int[]{compPrevPos[0], compPrevPos[1]});
            compRaw = 0;
        }
        if (cells[y][x] == Chars.O) {
            if (cells[prevPos[0]][prevPos[1]] == Chars.X) {
                compPrevPos[0] = -1;
                compPrevPos[1] = -1;
            }
            compRaw++;
            if (userPrevPos[0] >= 0 && userPrevPos[1] >= 0)
                userMoves.put(userRaw, new int[]{userPrevPos[0], userPrevPos[1]});
            userRaw = 0;
        }
        if (compRaw >= gameWinCount || userRaw >= gameWinCount) finalMessage();
        prevPos[0] = y;
        prevPos[1] = x;
    }

    private static void endOfBounds() {
        if (userPrevPos[0] >= 0 && userPrevPos[1] >= 0 && userRaw > 0)
            userMoves.put(userRaw, new int[]{userPrevPos[0], userPrevPos[1]});
        if (compPrevPos[0] >= 0 && compPrevPos[1] >= 0 && compRaw > 0)
            compMoves.put(compRaw, new int[]{compPrevPos[0], compPrevPos[1]});
    }

    private static void elementsToStartValues(int y, int x) {
        userRaw = 0;
        compRaw = 0;
        userPrevPos[0] = -1;
        userPrevPos[1] = -1;
        compPrevPos[0] = -1;
        compPrevPos[1] = -1;
        prevPos[0] = y;
        prevPos[1] = x;
    }

    private static ArrayList<Integer> getRandomNumbers() {
        ArrayList<Integer> numbersGenerated = new ArrayList<>();
        int directionsToCheck = 4;
        int randomNumber;
        for (int i = 0; i < directionsToCheck; i++) {
            randomNumber = RANDOM.nextInt(4) + 1;
            if (!numbersGenerated.contains(randomNumber)) numbersGenerated.add(randomNumber);
            else i--;
        }
        return numbersGenerated;
    }

    private static void finalMessage() {
        showField();
        if (currentChar == Chars.X) {
            System.out.println("Поздравляю, Вы победили!!!");
            System.exit(0);
        }
        if (currentChar == Chars.O) {
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

    private enum Chars {X, O, _;}
}

