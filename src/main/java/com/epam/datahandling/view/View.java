package com.epam.datahandling.view;

import com.epam.datahandling.lexis.Text;
import com.epam.datahandling.utils.TableGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class View {
    private static final int NUMBER_COLUMN_WIDTH = 10;
    private static final int SENTENCE_COLUMN_WIDTH = 50;
    private static final int WORD_COLUMN_WIDTH = 15;
    private static final Logger LOG = LogManager.getLogger(View.class);

    public void printParsedTextToFile(Text text, File outputFile, int maxSentenceLength) {
        if (outputFile.exists()) {
            try (
                FileOutputStream fileOutputStream = new FileOutputStream(outputFile, true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)
            ) {
                bufferedWriter.newLine();
                TableGenerator tableGenerator = new TableGenerator(
                    NUMBER_COLUMN_WIDTH, SENTENCE_COLUMN_WIDTH, WORD_COLUMN_WIDTH);
                String table = tableGenerator.generate(text, maxSentenceLength);
                bufferedWriter.write(table);
                bufferedWriter.flush();
            } catch (FileNotFoundException fileNotFoundException) {
                LOG.debug(fileNotFoundException.getMessage(), fileNotFoundException);
            } catch (IOException ioException) {
                LOG.debug("Unexpected IOException in the printParsedTextToFile method!", ioException);
            }
        }
    }
}
