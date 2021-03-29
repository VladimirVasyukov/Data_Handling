package com.epam.datahandling.lexis;

import com.epam.datahandling.utils.RegexProvider;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * Sentence is a sequence of characters separated by ".", "!", "?" and new line characters
 */
public class Sentence implements Comparable<Sentence>, Serializable {
    private static final long serialVersionUID = 2L;
    private static final String WORDS_DELIMITER_KEY = "wordDelimiter";
    private final String content;
    private Word[] words;

    public Sentence(String content) {
        this.content = content;
        setWords();
    }

    public String getContent() {
        return content;
    }

    public Word[] getWords() {
        return this.words.clone();
    }

    public void setWords() {
        String[] words = getContent().split(RegexProvider.get(WORDS_DELIMITER_KEY));
        int wordsLength = words.length;
        this.words = new Word[wordsLength];
        for (int i = 0; i < wordsLength; i++) {
            this.words[i] = new Word(words[i]);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sentence sentence = (Sentence) o;
        return Objects.equals(content, sentence.content) && Arrays.equals(words, sentence.words);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(content);
        result = 31 * result + Arrays.hashCode(words);
        return result;
    }

    @Override
    public int compareTo(Sentence othersentence) {
        return Integer.compare(this.getWords().length, othersentence.getWords().length);
    }

}
