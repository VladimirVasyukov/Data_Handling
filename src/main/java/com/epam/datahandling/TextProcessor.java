package com.epam.datahandling;

import com.epam.datahandling.lexis.Text;
import com.epam.datahandling.utils.RegexProvider;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class TextProcessor {
    private static final String MULTIPLE_SPACES_OR_TAB_KEY = "multipleSpacesOrTab";
    private static final String DEFAULT_TEXT = "Default text";
    private static final String ONE_SPACE_REPLACEMENT = " ";
    private static final Logger LOGGER = Logger.getLogger(TextProcessor.class);

    public Text parse(File src) {
        Text parsedText = new Text(DEFAULT_TEXT);
        try (
            FileInputStream fileInputStream = new FileInputStream(src);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8)
        ) {
            int i;
            List<Character> charsFromFile = new LinkedList<>();
            while ((i = inputStreamReader.read()) != -1) {
                charsFromFile.add((char) i);
            }
            parsedText = makeTextFromCharList (charsFromFile);
        } catch (FileNotFoundException fileNotFoundException) {
            LOGGER.debug(String.format("%s %s", src.getPath(),
                "source file not found! Default text has been returned."), fileNotFoundException);
        } catch (IOException ioException) {
            LOGGER.debug("Unexpected IOException in the parse method!", ioException);
        }
        return parsedText;
    }

    private Text makeTextFromCharList(List<Character> chars) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Character character : chars) {
            stringBuilder.append(character);
        }
        String textFromChars =
            stringBuilder.toString().
                replaceAll(RegexProvider.get(MULTIPLE_SPACES_OR_TAB_KEY), ONE_SPACE_REPLACEMENT).trim();
        return new Text(textFromChars);
    }

}
