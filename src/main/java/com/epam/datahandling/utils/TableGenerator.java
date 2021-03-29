package com.epam.datahandling.utils;

import com.epam.datahandling.lexis.Sentence;
import com.epam.datahandling.lexis.Text;
import com.epam.datahandling.lexis.Word;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

/**
 * Utility class creating table by input data
 */
public class TableGenerator {
    private static final String LINE_NUMBER_HEADER = "№";
    private static final String SENTENCE_HEADER = "Sentence";
    private static final String WORDS_COUNT_HEADER = "Word`s Count";
    private static final String VERTICAL_BAR = "|";
    private static final String HYPHEN = "-";
    private static final String EQUALS_SIGN = "=";
    private static final String WORDS_DELIMITER = " ";
    private static final String SINGLE_SPACE_KEY = "oneSpace";
    private static final String STRING_FORMAT = "s";
    private static final String TOO_SHORT_MAX_DATA_LENGTH_ERROR = "N/A";
    private static final int COLUMN_AMOUNT_OFFSET = VERTICAL_BAR.length() * 3;
    private static final int WIDTH_LIMITER = 2;
    private final int numberColumnWidth;
    private final int sentenceColumnWidth;
    private final int wordColumnWidth;

    public TableGenerator(int numberColumnWidth, int sentenceColumnWidth, int wordColumnWidth) {
        this.numberColumnWidth = numberColumnWidth;
        this.sentenceColumnWidth = sentenceColumnWidth;
        this.wordColumnWidth = wordColumnWidth;
    }

    public String generate(Text text, int maxDataLength) {
        return addHorizontalLine(EQUALS_SIGN) +
            addTextToCell(numberColumnWidth, LINE_NUMBER_HEADER) +
            addTextToCell(sentenceColumnWidth, SENTENCE_HEADER) +
            addTextToCell(wordColumnWidth, WORDS_COUNT_HEADER) +
            addRightVerticalMargin() +
            addHorizontalLine(EQUALS_SIGN) +
            addAllRows(text, maxDataLength);
    }

    private String addAllRows(Text text, int maxDataLength) {
        StringBuilder rowsBuilder = new StringBuilder();
        Sentence[] sortedSentences = text.getSentences().clone();
        Arrays.sort(sortedSentences);
        int sentencesLength = sortedSentences.length;
        for (int i = 0; i < sentencesLength; i++) {
            rowsBuilder.append(addTextToCell(numberColumnWidth, Integer.toString(i + 1)));
            Word[] words = sortedSentences[i].getWords();
            rowsBuilder.append(addSentenceWordToCell(words, maxDataLength));
            rowsBuilder.append(addTextToCell(wordColumnWidth, Integer.toString(sortedSentences[i].getWords().length)));
            rowsBuilder.append(addRightVerticalMargin());
            rowsBuilder.append(addHorizontalLine(HYPHEN));
        }
        return rowsBuilder.toString();
    }

    private String addSentenceWordToCell(Word[] words, int maxDataLength) {
        List<Word> wordsList = Arrays.asList(words);
        ListIterator<Word> wordListIterator = wordsList.listIterator();
        StringBuilder wordsStringBuilder = new StringBuilder();
        int wordsToLogLength = 0;
        while (wordListIterator.hasNext() && wordsToLogLength <= maxDataLength) {
            Word currentWord = wordListIterator.next();
            wordsToLogLength += currentWord.getContent().length();
            if (wordsToLogLength <= maxDataLength) {
                wordsStringBuilder.append(currentWord.getContent()).append(WORDS_DELIMITER);
                wordsToLogLength += WORDS_DELIMITER.length();
            }
        }

        String wordsFromSentence;
        if (wordsToLogLength == 0) {
            wordsFromSentence = addTextToCell(sentenceColumnWidth, TOO_SHORT_MAX_DATA_LENGTH_ERROR);
        } else {
            wordsFromSentence = addTextToCell(sentenceColumnWidth, wordsStringBuilder.toString().trim());
        }
        return wordsFromSentence;
    }

    private String addHorizontalLine(String fillSymbol) {
        return String.format("%s%<" + (numberColumnWidth + sentenceColumnWidth +
            wordColumnWidth + COLUMN_AMOUNT_OFFSET) + "s%n", VERTICAL_BAR).
            replaceAll(RegexProvider.get(SINGLE_SPACE_KEY), fillSymbol);
    }

    private String addTextToCell(int columnWidth, String outputText) {
        int columnWidthWithMargin = columnWidth + VERTICAL_BAR.length();
        int textAlighOffset = (int) Math.ceil((columnWidth + outputText.length()) / (double) WIDTH_LIMITER);
        int spacesBeforeText = columnWidthWithMargin - textAlighOffset;
        return String.format("%-" + spacesBeforeText + "s%-" + textAlighOffset + STRING_FORMAT, VERTICAL_BAR, outputText);
    }

    private String addRightVerticalMargin() {
        return String.format("%s", VERTICAL_BAR);
    }

}
