package main.java.lt.esdc.texthandler.parser.impl;

import main.java.lt.esdc.texthandler.component.ElementType;
import main.java.lt.esdc.texthandler.component.TextComposite;
import main.java.lt.esdc.texthandler.component.TextElement;
import main.java.lt.esdc.texthandler.parser.CustomParser;

import java.util.ArrayList;
import java.util.List;

public class CustomTextParser implements CustomParser {
    private final CustomParser next = new CustomParagraphParser();

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
