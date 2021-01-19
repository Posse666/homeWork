package ex1AndEx2;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        ex1();
        ex2();
    }

    private static void ex1() {
        String[] someStrings = new String[]{"One", "Two", "Three", "Four", "Five"};
        String[] switchedString = switchElements(someStrings, 1, 3);
        System.out.println(Arrays.toString(switchedString));

        Integer[] someNumbers = new Integer[]{1, 2, 3, 4, 5, 6, 7};
        Integer[] switchedNumbers = switchElements(someNumbers, 2, 5);
        System.out.println(Arrays.toString(switchedNumbers));
    }

    private static <T> T[] switchElements(T[] arr, int indexOne, int indexTwo) {
        if (indexOne >= arr.length || indexTwo >= arr.length) {
            System.err.println("Index out of bounds! Could not swap array elements!");
            return arr;
        } else {
            T tempElement = arr[indexOne];
            arr[indexOne] = arr[indexTwo];
            arr[indexTwo] = tempElement;
        }
        return arr;
    }

    private static void ex2() {
        String[] someStrings = new String[]{"One", "Two", "Three", "Four", "Five"};
        ArrayList<String> arrayOfStrings = toArray(someStrings);
        System.out.println(arrayOfStrings.toString());

        Object[] someObjects = new Object[]{1, "String", new Object(), new int[]{2, 3}, someStrings};
        ArrayList<Object> arrayOfObjects = toArray(someObjects);
        System.out.println(arrayOfObjects);
    }

    private static <T> ArrayList<T> toArray(T[] arr) {
        return new ArrayList<>(Arrays.asList(arr));
    }
}
