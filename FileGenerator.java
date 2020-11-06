package hw6;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Random;

public class FileGenerator {

    private final Random RANDOM = new Random();
    private StringBuilder searchString = new StringBuilder();

    public void generateTextFile(String inputFilename) throws IOException {
        int minimumLength = 50;
        int maximumLength = 101;
        int minimumChar = 48;
        int maximumChar = 126;
        int stringLowBound = 40;
        int stringHighBound = 20;
        int randomLength = RANDOM.nextInt(maximumLength - minimumLength) + minimumLength;
        FileOutputStream fos = new FileOutputStream(inputFilename);
        PrintStream ps = new PrintStream(fos);
        searchString.setLength(0);
        for (int j = 0; j < randomLength; j++) {
            int randomChar = RANDOM.nextInt(maximumChar - minimumChar) + minimumChar;
            ps.print((char) randomChar);
            if (j > randomLength - stringLowBound && j < randomLength - stringHighBound)
                searchString.append((char) randomChar);
        }
        ps.close();
    }

    public String getSearchString() {
        return searchString.toString();
    }
}
