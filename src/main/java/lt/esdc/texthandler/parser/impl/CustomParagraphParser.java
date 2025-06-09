package main.java.lt.esdc.texthandler.parser.impl;

import main.java.lt.esdc.texthandler.component.ElementType;
import main.java.lt.esdc.texthandler.component.TextComposite;
import main.java.lt.esdc.texthandler.component.TextElement;
import main.java.lt.esdc.texthandler.parser.CustomParser;

import java.util.ArrayList;
import java.util.List;

public class CustomParagraphParser implements CustomParser {
    private static final String PARAGRAPH_DELIMITER = "\\n{2,}|(?=^\\s{4})|(?=^\\t)";
    private final CustomParser next = new CustomSentenceParser();

    @Override
    public List<TextElement> parse(String text) {
        List<TextElement> paragraphs = new ArrayList<>();
        String[] paragraphsArray = text.split(PARAGRAPH_DELIMITER);

        for (String paragraph : paragraphsArray) {
            String trimmedParagraph = paragraph.trim();
            if (!trimmedParagraph.isEmpty()) {
                TextComposite paragraphComponent = new TextComposite(ElementType.PARAGRAPH);
                for (TextElement sentence : next.parse(trimmedParagraph)) {
                    paragraphComponent.add(sentence);
                }
                paragraphs.add(paragraphComponent);
            }
        }

        return paragraphs;
    }
}
