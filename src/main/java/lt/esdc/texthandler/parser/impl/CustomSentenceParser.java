package main.java.lt.esdc.texthandler.parser.impl;

import main.java.lt.esdc.texthandler.component.ElementType;
import main.java.lt.esdc.texthandler.component.TextComposite;
import main.java.lt.esdc.texthandler.component.TextElement;
import main.java.lt.esdc.texthandler.interpreter.ExpressionParser;
import main.java.lt.esdc.texthandler.parser.CustomParser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomSentenceParser implements CustomParser {
    private final static Pattern SENTENCE_PATTERN = Pattern.compile("[^.!?]*[.!?]");
    private final CustomParser next = new CustomLexemeParser();

    @Override
    public List<TextElement> parse(String text) {
        String processedText = ExpressionParser.parseAndEvaluate(text);

        List<TextElement> sentences = new ArrayList<>();
        Matcher matcher = SENTENCE_PATTERN.matcher(processedText);

        while (matcher.find()) {
            String sentence = matcher.group().trim();
            if (!sentence.isEmpty()) {
                TextComposite sentenceComponent = new TextComposite(ElementType.SENTENCE);
                for (TextElement lexeme : next.parse(sentence)) {
                    sentenceComponent.add(lexeme);
                }
                sentences.add(sentenceComponent);
            }
        }

        String remainingText = SENTENCE_PATTERN.matcher(processedText).replaceAll("").trim();
        if (!remainingText.isEmpty()) {
            TextComposite sentenceComponent = new TextComposite(ElementType.SENTENCE);
            for (TextElement lexeme : next.parse(remainingText)) {
                sentenceComponent.add(lexeme);
            }
            sentences.add(sentenceComponent);
        }

        return sentences;
    }
}
