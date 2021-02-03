import java.util.Arrays;

public class HomeWork6 {

    public int[] ex1(int... ints) {
        int[] result = null;
        if (ints.length == 0) return new int[0];
        for (int i = 0; i < ints.length - 1; i++) {
            if (ints[i] == 4) {
                result = Arrays.copyOfRange(ints, i + 1, ints.length);
            }
        }
        if (ints[ints.length - 1] == 4) return new int[0];
        if (result == null)
            throw new RuntimeException("Illegal array arguments. Must be at least one number 4! Your array: " + Arrays.toString(ints));
        return result;
    }

    public boolean ex2(int... ints) {
        boolean a = false;
        boolean b = false;
        for (int i = 0; i < ints.length; i++) {
            if (ints[i] == 1) {
                a = true;
            } else if (ints[i] == 4) {
                b = true;
            } else return false;
        }
        return a && b;
    }
}
