package hw6;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class FileAppender {
    FileOutputStream fos;
    PrintStream ps;
    FileInputStream fis;

    public void appendFiles(String inFile1, String inFile2, String outFile) throws IOException {
        fos = new FileOutputStream(outFile);
        ps = new PrintStream(fos);
        append(inFile1);
        append(inFile2);
        ps.close();
    }

    private void append(String inFileName) throws IOException {
        fis = new FileInputStream(inFileName);
        int singleChar;
        while ((singleChar = fis.read()) != -1) {
            ps.print((char) singleChar);
        }
        ps.println();
        fis.close();
    }
}
