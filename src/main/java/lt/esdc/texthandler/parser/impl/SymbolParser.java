package lt.esdc.texthandler.parser.impl;

import lt.esdc.texthandler.component.ElementType;
import lt.esdc.texthandler.component.TextElement;
import lt.esdc.texthandler.component.TextSymbol;
import lt.esdc.texthandler.parser.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SymbolParser implements Parser {
    private static final String PUNCTUATION = "\\p{Punct}";

    @Override
    public List<TextElement> parse(String text) {
        List<TextElement> characters = new ArrayList<>();

        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);

            if (Pattern.matches(PUNCTUATION, String.valueOf(character))) {
                characters.add(new TextSymbol(character, ElementType.PUNCTUATION));
            } else {
                characters.add(new TextSymbol(character, ElementType.LETTER));
            }
        }

        return characters;
    }
}
