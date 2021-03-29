package com.epam.datahandling.lexis;

import com.epam.datahandling.utils.RegexProvider;

import java.io.Serializable;

/**
 * Text to be parsed
 */
public class Text implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String SENTENCES_DELIMITER_KEY = "sentenceDelimiter";
    private final String content;
    private Sentence[] sentences;

    public Text(String content) {
        this.content = content;
        setSentences();
    }

    public String getContent() {
        return content;
    }

    public Sentence[] getSentences() {
        return sentences.clone();
    }

    public void setSentences() {
        String[] sentences = getContent().split(RegexProvider.get(SENTENCES_DELIMITER_KEY));
        int sentencesLength = sentences.length;
        this.sentences = new Sentence[sentencesLength];
        for (int i = 0; i < sentencesLength; i++) {
            this.sentences[i] = new Sentence(sentences[i]);
        }
    }

}
