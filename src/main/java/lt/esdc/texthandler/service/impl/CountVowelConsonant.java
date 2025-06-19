package lt.esdc.texthandler.service.impl;

import lt.esdc.texthandler.component.ElementType;
import lt.esdc.texthandler.component.TextElement;
import lt.esdc.texthandler.service.TextService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CountVowelConsonant implements TextService<Map<TextElement, CountVowelConsonant.VowelConsonantCount>> {
    private static final String VOWELS = "aeiouаеёиоуыэюяAEIOUАЕЁИОУЫЭЮЯ";

    public record VowelConsonantCount(int vowels, int consonants) {

        @Override
        public String toString() {
            return String.format("Vowel: %d, Consonant: %d", vowels, consonants);
        }
    }

    @Override
    public Map<TextElement, VowelConsonantCount> execute(TextElement textElement) {
        Map<TextElement, VowelConsonantCount> result = new LinkedHashMap<>();
        List<TextElement> sentences = getAllSentences(textElement);

        for (TextElement sentence : sentences) {
            VowelConsonantCount count = countVowelsConsonants(sentence);
            result.put(sentence, count);
        }

        return result;
    }

    private List<TextElement> getAllSentences(TextElement textElement) {
        List<TextElement> sentences = new ArrayList<>();
        collectSentences(textElement, sentences);
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

    private VowelConsonantCount countVowelsConsonants(TextElement sentence) {
        int vowels = 0;
        int consonants = 0;

        String sentenceText = sentence.toString();
        for (char c : sentenceText.toCharArray()) {
            if (Character.isLetter(c)) {
                if (VOWELS.indexOf(c) != -1) {
                    vowels++;
                } else {
                    consonants++;
                }
            }
        }

        return new VowelConsonantCount(vowels, consonants);
    }
}