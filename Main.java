package homeWork2;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final String[] message = new String[10];
    private static final Scanner scanner = new Scanner(System.in);
    private static final StringBuilder fullMessage = new StringBuilder();
    private static final Random random = new Random();
    private static boolean exit = false;

    public static void main(String[] args) {

        init();
        mainCycle();

    }

    private static void init() {

        message[1] = "1. Задать целочисленный массив, состоящий из элементов 0 и 1. Например: [ 1, 1, 0, 0, 1, 0, 1, 1, 0, 0 ]. Написать метод, заменяющий в принятом массиве 0 на 1, 1 на 0";
        message[2] = "2. Задать пустой целочисленный массив размером 8. Написать метод, который c помощью цикла заполнит его значениями 1 4 7 10 13 16 19 22";
        message[3] = "3. Задать массив [ 1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1 ], написать метод, принимающий на вход массив и умножающий числа меньше 6 на 2";
        message[4] = "4. Задать одномерный массив. Написать методы поиска в нём минимального и максимального элемента";
        message[5] = "5. Создать квадратный целочисленный массив (количество строк и столбцов одинаковое), заполнить его диагональные элементы единицами, используя цикл(ы)";
        message[6] = "6. Написать метод, в который передается не пустой одномерный целочисленный массив, метод должен вернуть true если в массиве есть место, в котором сумма левой и правой части массива равны. Примеры:\n" +
                "checkBalance([1, 1, 1, || 2, 1]) → true,\n" +
                "checkBalance ([2, 1, 1, 2, 1]) → false,\n" +
                "checkBalance ([10, || 1, 2, 3, 4]) → true.\n" +
                "Абстрактная граница показана символами ||, эти символы в массив не входят";
        message[7] = "7. Написать метод, которому на вход подаётся одномерный массив и число n (может быть положительным, или отрицательным), при этом метод должен циклически сместить все элементы массива на n позиций.\n" +
                "[1,2,3,4,5], -2 => [3,4,5,1,2]\n" +
                "[1,2,3,4,5], 2 => [4,5,1,2,3]\n" +
                "Не пользоваться вспомогательным массивом";
        message[8] = "8. Задание по хардкору. Нужен метод, который получает в параметры 2 массива (разной длины) int-чисел. Он (метод) должен вернуть массив значений, которые есть в 1 массиве, но их нет во втором";
        message[9] = "9. Условие: необходимо проверить, что первый двухмерный массив является подмассивом второго двухмерного массива, которые подаются в метод";
        message[0] = "0. ВЫХОД";

        buildFullMessage();

    }

    private static void mainCycle() {

        do {
            System.out.println(fullMessage);
            System.out.print("Выберите ДЗ, которое желаете проверить: ");
            int userChoice = getUserNumber();
            System.out.println(message[userChoice] + "\n");

            switch (userChoice) {
                case 1:
                    ex1();
                    break;
                case 2:
                    ex2();
                    break;
                case 3:
                    ex3();
                    break;
                case 4:
                    ex4();
                    break;
                case 5:
                    ex5();
                    break;
                case 6:
                    ex6();
                    break;
                case 7:
                    ex7();
                    break;
                case 8:
                    ex8();
                    break;
                case 9:
                    ex9();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Необходимо вводить варианты меню (0-8)!\nПопробуйте еще раз!");
                    break;
            }

            System.out.println();
            if (userChoice != 0) {
                System.out.print("Чтобы продолжить, введите что-нибудь: ");
                scanner.next();
            } else {
                System.out.println("До встречи!");
            }

        } while (!exit);

    }

    private static void ex1() {
        int arraySize = 10;
        int[] arrayBounds = {0, 1};
        int[] randomArray = getRandomArray(arraySize, arrayBounds);
        System.out.println("Ваш сгенерированный массив:\t" + Arrays.toString(randomArray));
        System.out.println("Ваш перевернутый массив:\t" + Arrays.toString(revertArray(randomArray)));
    }

    private static void ex2() {
        int arraySize = 8;
        int arrayStep = 3;
        int[] arrayBounds = {1, 22};
        int indexCounter = 0;
        int[] arrayForEx2 = new int[arraySize];
        for (int i = arrayBounds[0]; i <= arrayBounds[1]; i = i + arrayStep) {
            arrayForEx2[indexCounter] = i;
            indexCounter++;
        }
        System.out.println("Ваш массив через метод:\t" + Arrays.toString(arrayForEx2));
    }

    private static void ex3() {
        int arraySize = 12;
        int[] arrayBounds = {1, 11};
        int[] randomArray = getRandomArray(arraySize, arrayBounds);
        System.out.println("Ваш сгенерированный массив:\t" + Arrays.toString(randomArray));
        System.out.println("Ваш перемноженный массив:\t" + Arrays.toString(multipleArray(randomArray)));
    }

    private static void ex4() {
        int arraySize = random.nextInt(5) + 8;
        int[] arrayBounds = {0, 10};
        int[] randomArray = getRandomArray(arraySize, arrayBounds);
        System.out.println("Ваш сгенерированный массив:\t" + Arrays.toString(randomArray));
        System.out.println("Максимальный элемент:\t" + getMaxAndMinNumber(randomArray, "Max", arraySize) + "\nМинимальный элемент:\t" + getMaxAndMinNumber(randomArray, "Min", arraySize));
    }

    private static void ex5() {
        int arraySize = random.nextInt(7) + 8;
        int[][] squareArray = new int[arraySize][arraySize];
        for (int i = 0; i < arraySize; i++) {
            squareArray[i][i] = 1;
        }
        System.out.println("Ваш массив, заполненый по диагонали:\n");
        for (int i = 0; i < arraySize; i++) {
            System.out.println(Arrays.toString(squareArray[i]));
        }
    }

    private static void ex6() {
        int arraySize = random.nextInt(2) + 3;
        int[] arrayBounds = {0, 2};
        int[] randomArray = getRandomArray(arraySize, arrayBounds);
        System.out.println("Ваш сгенерированный массив:\t" + Arrays.toString(randomArray));
        System.out.println("Есть ли место, в котором сумма левой и правой части массива равны? " + checkBalance(randomArray));
    }

    private static void ex7() {
        int arraySize = random.nextInt(5) + 5;
        int arrayMove;
        do {
            arrayMove = 10 - random.nextInt(21);
        } while (arrayMove == 0);
        int[] array = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            array[i] = i + 1;
        }
        System.out.println("Ваш сгенерированный массив:\t" + Arrays.toString(array));
        System.out.println("Сдвиг: " + arrayMove);
        System.out.println("Ваш сдвинутый массив:\t\t" + Arrays.toString(getMovedArray(array, arrayMove)));
    }

    private static void ex8() {
        int array1Size = random.nextInt(8) + 7;
        int array2Size;
        do {
            array2Size = random.nextInt(8) + 7;
        } while (array1Size == array2Size);
        int[] arrayBounds = {0, 10};
        int[] randomArray1 = getRandomArray(array1Size, arrayBounds);
        int[] randomArray2 = getRandomArray(array2Size, arrayBounds);
        System.out.println("Ваш сгенерированный массив №1:\t" + Arrays.toString(randomArray1));
        System.out.println("Ваш сгенерированный массив №2:\t" + Arrays.toString(randomArray2));
        System.out.println("Ваш массив с разными числами:\t" + Arrays.toString(getMergedArray(randomArray1, randomArray2)));
    }

    private static void ex9() {
        int array2Size = random.nextInt(7) + 5;
        int array1Size;
        do {
            array1Size = random.nextInt(2) + 2;
        } while (array1Size == array2Size);
        int[][] squareArray1 = new int[array1Size][array1Size];
        int[][] squareArray2 = new int[array2Size][array2Size];
        int[] arrayBounds = {0, 2};
        for (int i = 0; i < array2Size; i++) {
            squareArray2[i] = getRandomArray(array2Size, arrayBounds);
            if (i < array1Size) {
                squareArray1[i] = getRandomArray(array1Size, arrayBounds);
            }
        }
        System.out.println("Ваш сгенерированный массив №1:\n");
        for (int i = 0; i < array1Size; i++) {
            System.out.println(Arrays.toString(squareArray1[i]));
        }
        System.out.println("\nВаш сгенерированный массив №2:\n");
        for (int i = 0; i < array2Size; i++) {
            System.out.println(Arrays.toString(squareArray2[i]));
        }
        System.out.println("\nВходит ли массив 2 в массив 1?\t" + compareArrays(squareArray1, squareArray2));
    }

    private static boolean compareArrays(int[][] array1, int[][] array2) {
        int array1Size = array1.length;
        int array2Size = array2.length;
        for (int i = 0; i < array2Size - array1Size; i++) {
            for (int j = 0; j < array2Size - array1Size; j++) {
                int sameNumbers = 0;
                if (array2[i][j] == array1[0][0]) {
                    for (int k = 0; k < array1Size; k++) {
                        for (int l = 0; l < array1Size; l++) {
                            if (array2[i + k][j + l] != array1[k][l]) {
                                k = array1Size;
                                break;
                            }
                            sameNumbers++;
                        }
                    }
                    if (sameNumbers == Math.pow(array1Size,2)) {
                        return true;
                    }
                }

            }
        }
        return false;
    }

    private static int[] getMergedArray(int[] array1, int[] array2) {
        int array1Size = array1.length;
        int array2Size = array2.length;
        int maxSize = array1Size;
        int minSize = array2Size;
        int[] maxArray = array1;
        int[] minArray = array2;

        if (array2Size > array1Size) {
            maxSize = array2Size;
            minSize = array1Size;
            maxArray = array2;
            minArray = array1;
        }

        int[] result = new int[maxSize];
        int resultIndex = 0;
        for (int i = 0; i < maxSize; i++) {
            int sameNumbers = 0;
            for (int j = 0; j < minSize; j++) {
                if (maxArray[i] == minArray[j]) {
                    sameNumbers++;
                }
            }
            if (sameNumbers == 0) {
                for (int k = 0; k < resultIndex; k++) {
                    if (maxArray[i] == result[k]) {
                        sameNumbers++;
                    }
                }
                if (sameNumbers == 0) {
                    result[resultIndex] = maxArray[i];
                    resultIndex++;
                }
            }
        }

        if (resultIndex != maxSize) {
            int[] tempResult = new int[resultIndex];
            for (int i = 0; i < resultIndex; i++) {
                tempResult[i] = result[i];
            }
            result = tempResult;
        }

        return result;
    }

    private static int[] getMovedArray(int[] arr, int move) {
        int arraySize = arr.length;
        move = move % arraySize;
        if (move < 0) {
            move = arraySize + move;
        }
        for (int i = 0; i < move; i++) {
            int tempFirstElement = arr[arraySize - 1];
            for (int j = arraySize - 1; j > 0; j--) {
                arr[j] = arr[j - 1];
            }
            //  System.arraycopy(arr, 0, arr, 1, arraySize - 1); В принципе, можно было и проще...
            arr[0] = tempFirstElement;
        }
        return arr;
    }

    private static boolean checkBalance(int[] array) {
        int arraySize = array.length;
        int sum = 0;
        for (int i = 0; i < arraySize; i++) {
            sum += array[i];
        }
        if (sum % 2 == 0) {
            for (int j = 0; j < arraySize; j++) {
                int sumLeft = 0;
                int sumRight = 0;
                for (int k = 0; k < arraySize; k++) {
                    if (j <= k) {
                        sumLeft += array[k];
                    } else {
                        sumRight += array[k];
                    }
                }
                if (sumLeft == sumRight) {
                    return true;
                }
            }
        }
        return false;
    }


    private static int getMaxAndMinNumber(int[] array, String arg, int arraySize) {
        int maxNumber = array[0];
        int minNumber = array[0];
        for (int i = 1; i < arraySize; i++) {
            if (maxNumber < array[i]) {
                maxNumber = array[i];
            }
            if (minNumber > array[i]) {
                minNumber = array[i];
            }
        }
        if (arg.equals("Max")) {
            return maxNumber;
        }
        return minNumber;
    }


    private static int[] revertArray(int[] array) {
        int arraySize = array.length;
        for (int i = 0; i < arraySize; i++) {
            if (array[i] == 0) {
                array[i] = 1;
            } else {
                array[i] = 0;
            }
        }
        return array;
    }

    private static int[] multipleArray(int[] array) {
        int arraySize = array.length;
        for (int i = 0; i < arraySize; i++) {
            if (array[i] < 6) {
                array[i] *= 2;
            }
        }
        return array;
    }

    private static int[] getRandomArray(int size, int[] bounds) {
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = random.nextInt(bounds[1] + 1) + bounds[0];
        }
        return result;
    }

    private static void buildFullMessage() {
        int messages = message.length;
        for (int i = 1; i < messages; i++) {
            fullMessage.append(message[i]).append("\n");
        }
        fullMessage.append(message[0]).append("\n");
    }

    private static int getUserNumber() {
        if (scanner.hasNextInt()) {
            return scanner.nextInt();
        }
        return -1;
    }

}

