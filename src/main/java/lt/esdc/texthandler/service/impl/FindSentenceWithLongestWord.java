package lt.esdc.texthandler.service.impl;

import lt.esdc.texthandler.component.ElementType;
import lt.esdc.texthandler.component.TextElement;
import lt.esdc.texthandler.service.TextService;

import java.util.ArrayList;
import java.util.List;

public class FindSentenceWithLongestWord implements TextService<TextElement> {
    private static final String CONTAINS_LETTER_REGEX = ".*\\p{L}+.*";
    private static final String NON_LETTER_CHARS_REGEX = "[^\\p{L}]";
    private static final String EMPTY_STRING = "";

    @Override
    public TextElement execute(TextElement textElement) {
        List<TextElement> allSentences = getAllSentences(textElement);

        if (allSentences.isEmpty()) {
            return null;
        }

        TextElement result = null;
        int maxWordLength = 0;

        for (TextElement sentence : allSentences) {
            int length = getMaxWordLengthInSentence(sentence);
            if (length > maxWordLength) {
                maxWordLength = length;
                result = sentence;
            }
        }

        return result;
    }

    private List<TextElement> getAllSentences(TextElement root) {
        List<TextElement> sentences = new ArrayList<>();
        collectSentences(root, sentences);
        return sentences;
    }

    private void collectSentences(TextElement element, List<TextElement> sentences) {
        if (element.getElementType() == ElementType.SENTENCE) {
            sentences.add(element);
        } else if (element.getChild() != null) {
            for (TextElement child : element.getChild()) {
                collectSentences(child, sentences);
            }
        }
    }

    private int getMaxWordLengthInSentence(TextElement sentence) {
        return sentence.getChild().stream()
                .filter(this::isWord)
                .mapToInt(this::getWordLength)
                .max()
                .orElse(0);
    }

    private boolean isWord(TextElement element) {
        if (element.getElementType() != ElementType.LEXEME) {
            return false;
        }
        return element.toString().matches(CONTAINS_LETTER_REGEX);
    }

    private int getWordLength(TextElement element) {
        return element.toString().replaceAll(NON_LETTER_CHARS_REGEX, EMPTY_STRING).length();
    }
}

