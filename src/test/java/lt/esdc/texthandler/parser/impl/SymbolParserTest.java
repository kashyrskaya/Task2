package lt.esdc.texthandler.parser.impl;

import lt.esdc.texthandler.component.*;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import static org.testng.Assert.*;
import java.util.List;

public class SymbolParserTest {

    private SymbolParser parser;

    @BeforeMethod
    public void setUp() {
        parser = new SymbolParser();
    }

    @Test
    public void testParseLetters() {
        List<TextElement> result = parser.parse("ABC");
        assertEquals(result.size(), 3);

        assertEquals(result.get(0).toString(), "A");
        assertEquals(result.get(0).getElementType(), ElementType.LETTER);

        assertEquals(result.get(1).toString(), "B");
        assertEquals(result.get(1).getElementType(), ElementType.LETTER);

        assertEquals(result.get(2).toString(), "C");
        assertEquals(result.get(2).getElementType(), ElementType.LETTER);
    }

    @Test
    public void testParsePunctuation() {
        List<TextElement> result = parser.parse(".,!");
        assertEquals(result.size(), 3);

        assertEquals(result.get(0).toString(), ".");
        assertEquals(result.get(0).getElementType(), ElementType.PUNCTUATION);

        assertEquals(result.get(1).toString(), ",");
        assertEquals(result.get(1).getElementType(), ElementType.PUNCTUATION);

        assertEquals(result.get(2).toString(), "!");
        assertEquals(result.get(2).getElementType(), ElementType.PUNCTUATION);
    }

    @Test
    public void testParseMixed() {
        List<TextElement> result = parser.parse("A,B!");
        assertEquals(result.size(), 4);

        assertEquals(result.get(0).getElementType(), ElementType.LETTER);
        assertEquals(result.get(1).getElementType(), ElementType.PUNCTUATION);
        assertEquals(result.get(2).getElementType(), ElementType.LETTER);
        assertEquals(result.get(3).getElementType(), ElementType.PUNCTUATION);
    }

    @Test
    public void testParseEmpty() {
        List<TextElement> result = parser.parse("");
        assertEquals(result.size(), 0);
    }
}