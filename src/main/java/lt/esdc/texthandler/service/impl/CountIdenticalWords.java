package lt.esdc.texthandler.service.impl;

import lt.esdc.texthandler.component.ElementType;
import lt.esdc.texthandler.component.TextElement;
import lt.esdc.texthandler.service.TextService;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CountIdenticalWords implements TextService<Map<String, Integer>> {
    @Override
    public Map<String, Integer> execute(TextElement textElement) {
        Map<String, Integer> wordCount = new HashMap<>();
        collectWords(textElement, wordCount);

        return wordCount.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    private void collectWords(TextElement element, Map<String, Integer> wordCount) {
        if (element.getElementType() == ElementType.LEXEME) {
            String raw = element.toString();
            if (containsLetter(raw)) {
                String word = extractWord(raw).toLowerCase();
                if (!word.isEmpty()) {
                    wordCount.merge(word, 1, Integer::sum);
                }
            }
        } else if (element.getChild() != null) {
            for (TextElement child : element.getChild()) {
                collectWords(child, wordCount);
            }
        }
    }

    private boolean containsLetter(String text) {
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (Character.isLetter(ch)) return true;
        }
        return false;
    }

    private String extractWord(String text) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (Character.isLetter(ch)) {
                sb.append(ch);
            }
        }
        return sb.toString();
    }
}

