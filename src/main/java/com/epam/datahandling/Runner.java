package com.epam.datahandling;

import com.epam.datahandling.controller.Controller;
import com.epam.datahandling.view.View;
/**
 * 08_DataHandling - Vladimir Vasyukov
 * Demo class demonstrating program capabilities of working with files, text parsing and displaying reports
 */
public class Runner {

    public static void main(String[] args) {
        Runner runner = new Runner();
        runner.run(args);
    }

    private void run(String[] args) {
        int resultSentenceLength = Integer.parseInt(args[0]);
        Controller controller = new Controller(new View(), new TextProcessor(), resultSentenceLength);
        controller.makeBackupFile();
        controller.parseTextFromFile();
        controller.exportParsedTextToFile();
        controller.serializeText();
    }

}
