package com.epam.datahandling.controller;

import com.epam.datahandling.TextProcessor;
import com.epam.datahandling.utils.BackupUtils;
import com.epam.datahandling.utils.TextSerializer;
import com.epam.datahandling.lexis.Text;
import com.epam.datahandling.view.View;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Controller {
    private static final String SOURCE_FILE_PATH = "src/main/resources/original/book.txt";
    private static final String BACKUP_FILE_PATH = "src/main/resources/backup/book.bak";
    private static final String SERIALIZED_FILE_PATH = "src/main/resources/backup/serializedText.txt";
    private static final File SOURCE_FILE = new File(SOURCE_FILE_PATH);
    private static final Logger LOG = Logger.getLogger(Controller.class);
    private final View view;
    private final TextProcessor textProcessor;
    private final int outputSentenceLength;
    private Text parsedText;

    public Controller(View view, TextProcessor textProcessor, int outputSentenceLength) {
        this.view = view;
        this.textProcessor = textProcessor;
        this.outputSentenceLength = outputSentenceLength;
    }

    public void makeBackupFile() {
        try {
            BackupUtils.backup(SOURCE_FILE_PATH, BACKUP_FILE_PATH);
        } catch (FileNotFoundException fileNotFoundException) {
            LOG.debug(String.format("%s %s", SOURCE_FILE_PATH,
                "source file not found! Backup has not been created."), fileNotFoundException);
        } catch (IOException ioException) {
            LOG.debug("Unexpected IOException in the backup method", ioException);
        }
    }

    public void parseTextFromFile() {
        this.parsedText = textProcessor.parse(SOURCE_FILE);
    }

    public void exportParsedTextToFile() {
        view.printParsedTextToFile(parsedText, SOURCE_FILE, outputSentenceLength);
    }

    public void serializeText() {
        TextSerializer textSerializer = new TextSerializer();
        textSerializer.serializeText(parsedText, new File(SERIALIZED_FILE_PATH));
    }

}
