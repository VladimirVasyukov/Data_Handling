package com.epam.datahandling.lexis;

import java.io.Serializable;
import java.util.Objects;

/**
 * Word is a sequence of characters separated by spaces and punctuation
 */
public class Word implements Serializable {
    private static final long serialVersionUID = 3L;
    private final String content;

    public Word(String word) {
        this.content = word;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Word word = (Word) o;
        return Objects.equals(content, word.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

}
