package hw6;

import java.io.*;

public class Main {

    private static String searchString;
    private static File folder = new File("D:/homeWork");

    public static void main(String[] args) {

        // 1. создание 2 файлов со случайным текстом + заполняем строку для поиска (вручную вбивать лень)
        FileGenerator fileGenerator = new FileGenerator();
        for (int i = 1; i < 3; i++) {
            try {
                fileGenerator.generateTextFile(folder + "/test" + i + ".txt");
            } catch (IOException e) {
                printWriteError(e);
            }
        }

        // 2. Склеивание 2 файлов в третий
        try {
            new FileAppender().appendFiles(folder + "/test1.txt", folder + "/test2.txt", folder + "/allIn.txt");
        } catch (IOException e) {
            printReadError(e);
        }

        // 3. Проверка содержания строки в файле
        String filename = folder + "/allIn.txt";
        searchString = fileGenerator.getSearchString();
        try {
            if (new WordSearcher().searchWord(searchString, filename)) printFound(filename);
            else printNotFound(filename);
        } catch (IOException e) {
            printReadError(e);
        }

        //4. Проверка всех файлов в папке на предмет искомой строки
        String[] files = folder.list();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                try {
                    if (new WordSearcher().searchWord(searchString, folder + "/" + files[i])) printFound(files[i]);
                    else printNotFound(files[i]);
                } catch (IOException e) {
                    printReadError(e);
                }
            }
        } else {
            System.out.println("Пустая папка!");
        }
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