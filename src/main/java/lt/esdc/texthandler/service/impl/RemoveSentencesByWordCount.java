package lt.esdc.texthandler.service.impl;

import lt.esdc.texthandler.component.ElementType;
import lt.esdc.texthandler.component.TextElement;
import lt.esdc.texthandler.service.TextService;

import java.util.ArrayList;
import java.util.List;

public class RemoveSentencesByWordCount implements TextService<TextElement> {
    private final int minWordCount;

    public RemoveSentencesByWordCount(int minWordCount) {
        this.minWordCount = minWordCount;
    }

    @Override
    public TextElement execute(TextElement textElement) {
        return filterElement(textElement);
    }

    private TextElement filterElement(TextElement element) {
        if (element.getElementType() == ElementType.SENTENCE) {
            int wordCount = countWordsInSentence(element);
            return wordCount >= minWordCount ? element : null;
        }

        if (element.getChild() != null) {
            List<TextElement> filteredChildren = new ArrayList<>();

            for (TextElement child : element.getChild()) {
                TextElement filtered = filterElement(child);
                if (filtered != null) {
                    filteredChildren.add(filtered);
                }
            }

            if (!filteredChildren.isEmpty()) {
                TextElement newElement = new lt.esdc.texthandler.component.TextComposite(element.getElementType());
                filteredChildren.forEach(newElement::add);
                return newElement;
            }
        }

        return null;
    }

    private int countWordsInSentence(TextElement sentence) {
        return (int) sentence.getChild().stream()
                .filter(this::isWord)
                .count();
    }

    private boolean isWord(TextElement lexeme) {
        if (lexeme.getElementType() != ElementType.LEXEME) {
            return false;
        }

        String text = lexeme.toString();
        for (int i = 0; i < text.length(); i++) {
            if (Character.isLetter(text.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}

