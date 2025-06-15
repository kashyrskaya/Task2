package lt.esdc.texthandler.parser.impl;

import lt.esdc.texthandler.component.ElementType;
import lt.esdc.texthandler.component.TextComposite;
import lt.esdc.texthandler.component.TextElement;
import lt.esdc.texthandler.interpreter.ExpressionParser;
import lt.esdc.texthandler.parser.Parser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentenceParser implements Parser {
    private static final Logger logger = LogManager.getLogger(SentenceParser.class);
    private final static Pattern SENTENCE_PATTERN = Pattern.compile("[^.!?]*[.!?]");
    private final Parser next = new LexemeParser();

    @Override
    public List<TextElement> parse(String text) {
        String processedText = ExpressionParser.parseAndEvaluate(text);

        List<TextElement> sentences = new ArrayList<>();
        Matcher matcher = SENTENCE_PATTERN.matcher(processedText);

        while (matcher.find()) {
            String sentence = matcher.group().trim();
            if (!sentence.isEmpty()) {
                TextComposite sentenceComponent = new TextComposite(ElementType.SENTENCE);
                logger.info("Parsing " + sentence);
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
