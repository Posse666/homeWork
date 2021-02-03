import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class HomeWork6Test {

    private HomeWork6 hw;

    @BeforeEach
    void setUp() {
        hw = new HomeWork6();
    }

    @CsvSource({
            "'1 2 4 4 3 4 1 7','1 7'",
            "'1 2 3 5 5 8 2 4', ''",
            "'2 4 5 6 8', '5 6 8'",
            "Some incorrect string, test output"
    })
    @ParameterizedTest
    void testEx1(String in, String expected) {
        Assertions.assertEquals(
                Arrays.toString(convertStringToInt(expected, " ")),
                Arrays.toString(hw.ex1(convertStringToInt(in, " ")))
        );
    }

    @CsvSource({
            "1 2 3",
            "5 99 22"
    })
    @ParameterizedTest
    void ex1WaitException(String in) {
        int[] ints = convertStringToInt(in, " ");
        RuntimeException runtimeException = assertThrows(
                RuntimeException.class, () -> hw.ex1(ints)
        );
        Assertions.assertEquals(
                "Illegal array arguments. Must be at least one number 4! Your array: " + Arrays.toString(ints),
                runtimeException.getMessage()
        );
    }

    @CsvSource({
            "1 4 1 4 1 1 4 4 4 4 4 1 1",
            "1 1 1 1 1 1 1 4",
            "4 4 4 4 1 4 4 4 4",
    })
    @ParameterizedTest
    void testEx2asTrue(String in) {
        Assertions.assertTrue(hw.ex2(convertStringToInt(in, " ")));
    }

    @CsvSource({
            "1 4 1 4 1 1 2 4 4 4 4 1 1",
            "1 1 1 1 1 1 1 1",
            "4 4 4 4 4 4 4 4 4",
            "Some test"
    })
    @ParameterizedTest
    void testEx2asFalse(String in) {
        Assertions.assertFalse(hw.ex2(convertStringToInt(in, " ")));
    }

    private static int[] convertStringToInt(String input, String regex) {
        int[] result = new int[0];
        try {
            result = Arrays.stream(input.split(regex)).mapToInt(Integer::parseInt).toArray();
        } catch (NumberFormatException e) {
            System.err.println("Illegal array input! Must be numbers! Your array: " + input);
        }
        return result;
    }
}