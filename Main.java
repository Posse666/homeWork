import java.io.*;
import java.util.Random;

public class Main {
    private static final Random random = new Random();
    private static StringBuilder searchString = new StringBuilder();
    private static File folder = new File("C:/Users/VerushkinRG/Desktop/Личное/Java/hw6");
    private static FileOutputStream fos;
    private static FileInputStream fis;
    private static PrintStream ps;

    public static void main(String[] args) {

        // 1. создание 2 файлов со случайным текстом + заполняем строку для поиска (вручную вбивать лень)
        for (int i = 1; i < 3; i++) {
            generateTextFile("test" + i + ".txt");
        }

        // 2. Склеивание 2 файлов в третий
        appendFiles("test1.txt", "test2.txt", "allIn.txt");

        // 3. Проверка содержания строки в файле
        String filename = "allIn.txt";
        if (searchWord(searchString.toString(), filename)) printFound(filename);
        else printNotFound(filename);

        //4. Проверка всех файлов в папке на предмет искомой строки
        String[] files = folder.list();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (searchWord(searchString.toString(), files[i])) printFound(files[i]);
                else printNotFound(files[i]);
            }
        } else {
            System.out.println("Пустая папка!");
        }
    }

    private static void generateTextFile(String inputFilename) {
        int randomLength = random.nextInt(51) + 50;
        try {
            fos = new FileOutputStream(folder + "/" + inputFilename);
            ps = new PrintStream(fos);
            searchString.setLength(0);
            for (int j = 0; j < randomLength; j++) {
                int randomChar = random.nextInt(78) + 48;
                ps.print((char) randomChar);
                if (j > randomLength - 40 && j < randomLength - 20) searchString.append((char) randomChar);
            }
            ps.close();
        } catch (IOException e) {
            printWriteError(e);
        }
    }

    private static void appendFiles(String inFile1, String inFile2, String outFile) {
        try {
            fos = new FileOutputStream(folder + "/" + outFile);
        } catch (IOException e) {
            printReadError(e);
        }
        ps = new PrintStream(fos);
        append(inFile1);
        append(inFile2);
        ps.close();
    }

    private static void append(String inFileName) {
        try {
            fis = new FileInputStream(folder + "/" + inFileName);
            int singleChar;
            while ((singleChar = fis.read()) != -1) {
                ps.print((char) singleChar);
            }
            ps.println();
            fis.close();
        } catch (IOException e) {
            printWriteError(e);
        }
    }

    private static boolean searchWord(String inputString, String inputFileName) {
        StringBuilder tempString = new StringBuilder();
        try {
            fis = new FileInputStream(folder + "/" + inputFileName);
            int singleChar;
            while ((singleChar = fis.read()) != -1) {
                tempString.append((char) singleChar);
            }
            fis.close();
        } catch (IOException e) {
            printReadError(e);
        }
        return tempString.toString().contains(inputString);
    }

    private static void printReadError(Exception e) {
        System.out.println("Ошибка чтения: " + e.getMessage());
    }

    private static void printWriteError(Exception e) {
        System.out.println("Ошибка записи: " + e.getMessage());
    }

    private static void printFound(String filename) {
        System.out.println("Строка найдена! в файле: " + filename);
    }

    private static void printNotFound(String filename) {
        System.out.println("Ничего не нашлось в файле: " + filename);
    }
}
