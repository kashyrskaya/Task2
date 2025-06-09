package main.java.lt.esdc.texthandler.parser.impl;

import main.java.lt.esdc.texthandler.component.ElementType;
import main.java.lt.esdc.texthandler.component.TextComposite;
import main.java.lt.esdc.texthandler.component.TextElement;
import main.java.lt.esdc.texthandler.parser.CustomParser;

import java.util.ArrayList;
import java.util.List;

public class CustomLexemeParser implements CustomParser {
    private final static String LEXEMES_DELIMITER = "\\s+";
    private final static String EMPTY_WORD = "";
    private final CustomParser next = new CustomSymbolParser();

    @Override
    public List<TextElement> parse(String text) {
        List<TextElement> lexemes = new ArrayList<>();
        String[] lexemesArray = text.split(LEXEMES_DELIMITER);

        for (String lexeme : lexemesArray) {
            if (!lexeme.trim().equals(EMPTY_WORD)) {
                TextComposite lexemeComponent = new TextComposite(ElementType.LEXEME);
                for (TextElement symbol : next.parse(lexeme.trim())) {
                    lexemeComponent.add(symbol);
                }
                lexemes.add(lexemeComponent);
            }
        }

        return lexemes;
    }
}
