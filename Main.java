/*
        Есть строка вида: "10 3 1 2\n2 3 2 2\n5 6 7 1\n300 3 1 0"; (другими словами матрица 4x4)

        10 3 1 2
        2 3 2 2
        5 6 7 1
        300 3 1 0

        1. Написать метод, на вход которого подаётся такая строка, метод должен преобразовать строку в двумерный массив типа String[][];
        2. Преобразовать все элементы массива в числа типа int, просуммировать, поделить полученную сумму на 2, и вернуть результат;
        3. Ваши методы должны бросить исключения в случаях:
        Если размер матрицы, полученной из строки, не равен 4x4;
        Если в одной из ячеек полученной матрицы не число; (например символ или слово)
        4. В методе main необходимо вызвать полученные методы, обработать возможные исключения и вывести результат расчета.
        5. * Написать собственные классы исключений для каждого из случаев
*/

public class Main {

    private static final String testString = "10 3 1 2\n2 3 2 2\n5 6 7 1\n300 3 1 0";

    public static void main(String[] args) {

        try {
            String[][] result = stringToElements(testString, "\n", " ");
            printMatrix(result);

            try {
                System.out.println(someMath(result));
            } catch (WrongInputStringException e) {
                System.out.println("Put here some method to do something...");
                e.printStackTrace();
            }

        } catch (Need4x4MatrixStringException e) {
            System.out.println("Put here some method to do something...");
            e.printStackTrace();
        }
    }

    private static String[][] stringToElements(String inputString, String regexString, String regexColumn) throws Need4x4MatrixStringException {
        int stringAndColumnValue = 4;

        String[] tempString = inputString.split(regexString);
        if (tempString.length != stringAndColumnValue)
            throw new Need4x4MatrixStringException("String numbers != " + stringAndColumnValue + ". It's value is: " + tempString.length);

        String[][] result = new String[tempString.length][];
        for (int i = 0; i < tempString.length; i++) {
            result[i] = tempString[i].split(regexColumn);
            if (result[i].length != stringAndColumnValue)
                throw new Need4x4MatrixStringException("Column numbers in row " + (i + 1) + " != " + stringAndColumnValue + ". It's value is: " + result[i].length);
        }
        return result;
    }

    private static int someMath(String[][] matrix) throws WrongInputStringException {
        int sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                try {
                    sum += Integer.parseInt(matrix[i][j]);
                } catch (NumberFormatException e) {
                    throw new WrongInputStringException("Need number in row: " + (i + 1) + " and column: " + (j + 1) + ". Now it's: " + matrix[i][j]);
                }
            }
        }
        return sum / 2;
    }

    private static void printMatrix(String[][] printable) {
        for (int i = 0; i < printable.length; i++) {
            for (int j = 0; j < printable[i].length; j++) {
                System.out.print(printable[i][j] + " ");
            }
            System.out.println();
        }
    }
}
