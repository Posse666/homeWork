package hw6;

import java.io.FileInputStream;
import java.io.IOException;

public class WordSearcher {

    public boolean searchWord(String inputString, String inputFileName) throws IOException {
        StringBuilder tempString = new StringBuilder();
        FileInputStream fis = new FileInputStream(inputFileName);
        int singleChar;
        while ((singleChar = fis.read()) != -1) {
            tempString.append((char) singleChar);
        }
        fis.close();
        return tempString.toString().contains(inputString);
    }
}
