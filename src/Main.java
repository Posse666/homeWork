import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final String[] message = new String[8];

    public static void main(String[] args) {

        message[0] = "1. Создать пустой проект в IntelliJ IDEA и прописать метод main().";
        message[1] = "2. Создать переменные всех пройденных типов данных и инициализировать их значения.";
        message[2] = "3. Написать метод вычисляющий выражение a * (b + (c / d)) и возвращающий результат, где a, b, c, d – входные параметры этого метода.";
        message[3] = "4. Написать метод, принимающий на вход два числа и проверяющий, что их сумма лежит в пределах от 10 до 20 (включительно), если да – вернуть true, в противном случае – false.";
        message[4] = "5. Создать метод, который принимает число. Если данное число больше 100 и меньше 999 включительно - вывести в консоль цифры данного числа в обратном порядке. Например, введено число 725 -> в консоле будет: 527.";
        message[5] = "6. Написать метод, который определяет является ли год високосным, и выводит сообщение в консоль. Каждый 4-й год является високосным, кроме каждого 100-го, при этом каждый 400-й – високосный.";
        message[6] = "7. Обязательно ознакомиться с Git";
        message[7] = "0. ВЫХОД";

        boolean exit = false;

        StringBuilder fullMessage = new StringBuilder();
        for (String s : message) {
            fullMessage.append(s).append("\n");
        }
        int[] userNumbers = new int[1];

        do {
            userNumbers = getUserNumbers(userNumbers.length, "Выберите ДЗ, которое желаете проверить:\n" + fullMessage);

            switch (userNumbers[0]) {
                case 1:
                    System.out.println("Ну, видимо, сделано, раз программа запустилась ;)");
                    break;
                case 2:
                    init();
                    break;
                case 3:
                    math();
                    break;
                case 4:
                    checkLittleNumbers();
                    break;
                case 5:
                    revertNumbers();
                    break;
                case 6:
                    yearCheck();
                    break;
                case 7:
                    System.out.println("Постарался сдать через Git.\nЕсли Вы это читаете, значит получилось!");
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Необходимо вводить варианты меню (0-7)!\nПопробуйте ещераз!");
                    break;
            }

            System.out.println();
            if (userNumbers[0] != 0) {
                Scanner s = new Scanner(System.in);
                System.out.println("Чтобы продолжить нажмите ВВОД");
                s.nextLine();
            } else {
                System.out.println("До встречи!");
            }

        } while (!exit);
    }

    private static void init() {

        ArrayList<Object> objects = new ArrayList<>();

        int intValue = 5;
        long longValue = 2020L;
        short shortValue = 4;
        byte byteValue = 127;
        double doubleValue = 2.2d;
        float floatValue = 7.7f;
        char charValue = 'X';
        boolean booleanValue = true;
        String string = "Some string";

        objects.add(intValue);
        objects.add(longValue);
        objects.add(shortValue);
        objects.add(byteValue);
        objects.add(doubleValue);
        objects.add(floatValue);
        objects.add(charValue);
        objects.add(booleanValue);
        objects.add(string);

        System.out.println("\n" + message[1] + "\n");
        for (Object object : objects) {
            System.out.println(object.getClass() + " = " + object.toString());
        }
    }

    private static void math() {
        int[] userNumbers = writeHeadAndGetNumbers(2, 4, "Последовательно введите a, b, c и d:");
        float answer = (userNumbers[0] * (userNumbers[1] + (userNumbers[2] / (float) userNumbers[3])));
        System.out.println("Ответ на выражение a * (b + (c / d)) = " + answer);
    }

    private static void checkLittleNumbers() {
        int[] userNumbers = writeHeadAndGetNumbers(3, 2, "Введите 2 числа:");
        boolean trueCheck = false;
        if (10 <= userNumbers[0] + userNumbers[1] && userNumbers[0] + userNumbers[1] <= 20) {
            trueCheck = true;
        }
        System.out.println("Лежит ли сумма этих чисел в пределах от 10 до 20 (включительно)?\n" + trueCheck);
    }

    private static void revertNumbers() {
        int[] userNumbers = writeHeadAndGetNumbers(4, 2, "Введите 2 числа:");
        int summ = userNumbers[0] + userNumbers[1];
        System.out.println("Сумма введенных вами цифр: " + summ);
        if (100 <= summ && summ <= 999) {
            StringBuilder revertString = new StringBuilder();
            while (summ > 0) {
                revertString.append(summ % 10);
                summ = summ / 10;
            }
            System.out.println("Ваша перевернутая цифра: " + revertString);
        } else {
            System.out.println("Сумма чисел не прошла в заданный диапазон (100-999)...");
        }
    }

    private static void yearCheck() {
        int[] userNumbers = writeHeadAndGetNumbers(5, 1, "Введите год:");
        if (((userNumbers[0] % 4 == 0) && (userNumbers[0] % 100 != 0)) || (userNumbers[0] % 400 == 0)) {
            System.out.println("Поздравляем, введеный год является високосным!");
        } else {
            System.out.println("Введеный год не високосный");
        }
    }

    private static int[] writeHeadAndGetNumbers(int messageNumber, int numbersNeeded, String userMessage) {
        System.out.println("\n" + message[messageNumber] + "\n");
        int[] userNumbers = new int[numbersNeeded];
        userNumbers = getUserNumbers(userNumbers.length, userMessage);
        return userNumbers;
    }

    private static int[] getUserNumbers(int length, String message) {
        System.out.println(message);
        int[] userInput = new int[length];
        for (int i = 0; i < length; i++) {
            Scanner in = new Scanner(System.in);
            if (in.hasNextInt()) {
                userInput[i] = in.nextInt();
            } else {
                i--;
                System.out.println("Необходимо вводить число");
            }
        }
        return userInput;
    }
}
