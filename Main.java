import java.util.Arrays;

public class Main {

    private static final int SIZE = 10_000_000;
    private static final int HALF = SIZE / 2;
    private static final float[] arr = new float[SIZE];

    private static class ThreadArrayCount extends Thread {

        float[] arr;
        int startPosition;

        ThreadArrayCount(String name, float[] arr, int startPosition) {
            super(name);
            this.arr = arr;
            this.startPosition = startPosition;
            start();
        }

        @Override
        public void run() {
            for (int i = startPosition; i < arr.length + startPosition; i++) {
                arr[i - startPosition] = (float) (arr[i - startPosition] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        }
    }

    public static void main(String[] args) {
        method1();
        method2();
    }

    private static void method1() {
        Arrays.fill(arr, 1);
        long a = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("Single thread run time: " + (System.currentTimeMillis() - a));
        printStartAndEndOfArray();
    }

    private static void method2() {
        Arrays.fill(arr, 1);
        float[] a1 = new float[HALF];
        float[] a2 = new float[HALF];
        long a = System.currentTimeMillis();
        System.arraycopy(arr, 0, a1, 0, HALF);
        System.arraycopy(arr, HALF, a2, 0, HALF);
        ThreadArrayCount t1 = new ThreadArrayCount("first half", a1, 0);
        ThreadArrayCount t2 = new ThreadArrayCount("second half", a2, HALF);
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(a1, 0, arr, 0, HALF);
        System.arraycopy(a2, 0, arr, HALF, HALF);
        System.out.println("Double thread run time: " + (System.currentTimeMillis() - a));
        printStartAndEndOfArray();
    }

    private static void printStartAndEndOfArray() {
        int neededArrayElementsToCheck = 5;
        for (int i = 0; i < neededArrayElementsToCheck; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.print("...... ");
        for (int i = arr.length - neededArrayElementsToCheck; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
