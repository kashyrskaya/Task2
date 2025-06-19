package lt.esdc.texthandler.parser.impl;

import lt.esdc.texthandler.component.ElementType;
import lt.esdc.texthandler.component.TextComposite;
import lt.esdc.texthandler.component.TextElement;
import lt.esdc.texthandler.parser.Parser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ParagraphParser implements Parser {
    private static final Logger logger = LogManager.getLogger(ParagraphParser.class);
    private static final Pattern PARAGRAPH_PATTERN = Pattern.compile("(\\r?\\n){2,}|^\\s{4,}|^\\t", Pattern.MULTILINE);
    private final Parser next = new SentenceParser();

    @Override
    public List<TextElement> parse(String text) {
        List<TextElement> paragraphs = new ArrayList<>();
        String[] paragraphsArray = PARAGRAPH_PATTERN.split(text);

        for (String paragraph : paragraphsArray) {
            String trimmedParagraph = paragraph.stripTrailing();
            if (!trimmedParagraph.isEmpty()) {
                TextComposite paragraphComponent = new TextComposite(ElementType.PARAGRAPH);
                logger.info("{} is being parsed", paragraphComponent);
                for (TextElement sentence : next.parse(trimmedParagraph)) {
                    paragraphComponent.add(sentence);
                }
                paragraphs.add(paragraphComponent);
            }
        }

        logger.info("{} paragraphs parsed", paragraphs.size());
        return paragraphs;
    }
}
