package lt.esdc.texthandler.parser.impl;

import lt.esdc.texthandler.component.ElementType;
import lt.esdc.texthandler.component.TextElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class LexemeParserTest {

    private LexemeParser parser;

    @BeforeMethod
    public void setUp() {
        parser = new LexemeParser();
    }

    @Test
    public void testParseSingleWord() {
        List<TextElement> result = parser.parse("Hello");
        assertEquals(result.size(), 1);

        TextElement lexeme = result.get(0);
        assertEquals(lexeme.getElementType(), ElementType.LEXEME);
        assertEquals(lexeme.toString(), "Hello");
    }

    @Test
    public void testParseMultipleWords() {
        List<TextElement> result = parser.parse("Hello World");
        assertEquals(result.size(), 2);

        assertEquals(result.get(0).toString(), "Hello");
        assertEquals(result.get(1).toString(), "World");
    }

    @Test
    public void testParseWithExtraSpaces() {
        List<TextElement> result = parser.parse("  Hello   World  ");
        assertEquals(result.size(), 2);

        assertEquals(result.get(0).toString(), "Hello");
        assertEquals(result.get(1).toString(), "World");
    }

    @Test
    public void testParseEmpty() {
        List<TextElement> result = parser.parse("");
        assertEquals(result.size(), 0);
    }

    @Test
    public void testParseOnlySpaces() {
        List<TextElement> result = parser.parse("   ");
        assertEquals(result.size(), 0);
    }
}