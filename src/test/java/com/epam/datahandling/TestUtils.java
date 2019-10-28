package com.epam.datahandling;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TestUtils {

    public static File prepareSource(String input) throws IOException {
        File src = File.createTempFile("datahandling", ".txt");
        writeToFile(src, input);
        src.deleteOnExit();
        return src;
    }

    public static File prepareDestination() throws IOException {
        File dest = File.createTempFile("datahandling", ".txt");
        dest.deleteOnExit();
        return dest;
    }

    public static void writeToFile(File file, String input) {
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(input);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFromFile(File file) {
        StringBuilder result = new StringBuilder();
        try {
            FileReader fr = new FileReader(file);
            int next;
            while((next = fr.read()) != -1) {
                result.append((char) next);
            }
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

}
