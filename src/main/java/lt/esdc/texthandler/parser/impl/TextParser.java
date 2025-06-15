package lt.esdc.texthandler.parser.impl;

import lt.esdc.texthandler.component.ElementType;
import lt.esdc.texthandler.component.TextComposite;
import lt.esdc.texthandler.component.TextElement;
import lt.esdc.texthandler.parser.Parser;

import java.util.ArrayList;
import java.util.List;

public class TextParser implements Parser {
    private final Parser next = new ParagraphParser();

    @Override
    public List<TextElement> parse(String text) {
        List<TextElement> texts = new ArrayList<>();
        TextComposite textComponent = new TextComposite(ElementType.TEXT);

        for(TextElement paragraph : next.parse(text)) {
            textComponent.add(paragraph);
        }

        texts.add(textComponent);
        return texts;
        }
    }
