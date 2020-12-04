/*
        1. Создать массив с набором слов (20-30 слов, должны встречаться повторяющиеся):
        - Найти список слов, из которых состоит текст (дубликаты не считать);
        - Посчитать сколько раз встречается каждое слово (использовать HashMap);
*/

import java.util.*;

public class Main1 {

    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        String[] generatedArray = generateStringArray();
        System.out.println(Arrays.toString(generatedArray));
        System.out.println(getUniqueWords(generatedArray));
        System.out.println(countUsedWords(generatedArray));
    }

    private static HashSet<String> getUniqueWords(String[] inputArray) {
        return new HashSet<>(Arrays.asList(inputArray));
    }

    private static HashMap<String, Integer> countUsedWords(String[] inputString) {
        HashMap<String, Integer> uniqueWordsCounter = new HashMap<>();
        ArrayList<String> inputArray = new ArrayList<>(Arrays.asList(inputString));
        HashSet<String> uniqueWords = getUniqueWords(inputString);
        for (String key : uniqueWords) {
            uniqueWordsCounter.put(key, Collections.frequency(inputArray, key));
        }
        return uniqueWordsCounter;
    }

    private static String[] generateStringArray() {
        int minimumLength = 20;
        int maximumLength = 30;
        ArrayList<String> tempList = new ArrayList<>();
        int randomLength = RANDOM.nextInt(maximumLength - minimumLength) + minimumLength;
        for (int i = 0; i < randomLength; i++) {
            tempList.add(getGeneratedWord());
            if (RANDOM.nextInt(4) > 2) {
                tempList.add(tempList.get(RANDOM.nextInt(tempList.size())));
            }
        }
        return tempList.toArray(new String[0]);
    }

    private static String getGeneratedWord() {
        int minimumChar = 65;
        int maximumChar = 123;
        int maximumBigLetterChar = 90;
        int minimumSmallLetterChar = 97;
        int randomChar;
        StringBuilder generatedWord = new StringBuilder();
        do {
            do {
                randomChar = RANDOM.nextInt(maximumChar - minimumChar) + minimumChar;
            } while (randomChar > maximumBigLetterChar && randomChar < minimumSmallLetterChar);
            generatedWord.append((char) randomChar);
        } while (RANDOM.nextInt(10) <= 7);
        return generatedWord.toString();
    }
}
