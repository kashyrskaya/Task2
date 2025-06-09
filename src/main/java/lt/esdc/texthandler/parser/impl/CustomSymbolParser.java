package main.java.lt.esdc.texthandler.parser.impl;

import main.java.lt.esdc.texthandler.component.TextElement;
import main.java.lt.esdc.texthandler.component.TextSymbol;
import main.java.lt.esdc.texthandler.parser.CustomParser;
import main.java.lt.esdc.texthandler.component.ElementType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CustomSymbolParser implements CustomParser {
    private static final String SYMBOLS_DELIMITER = "";
    private static final String PUNCTUATION = "\\p{Punct}";

    @Override
    public List<TextElement> parse(String text) {
        List<TextElement> characters = new ArrayList<>();

        for (int i = 0; i < text.length(); i++) {
            String character = String.valueOf(text.charAt(i));

            if (Pattern.matches(PUNCTUATION, character)) {
                characters.add(new TextSymbol(character, ElementType.PUNCTUATION));
            } else {
                characters.add(new TextSymbol(character, ElementType.LETTER));
            }
        }

        return characters;
    }
}
