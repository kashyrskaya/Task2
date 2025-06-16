package lt.esdc.texthandler.parser.impl;

import lt.esdc.texthandler.component.ElementType;
import lt.esdc.texthandler.component.TextElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class ParagraphParserTest {

    private ParagraphParser parser;

    @BeforeMethod
    public void setUp() {
        parser = new ParagraphParser();
    }

    @Test
    public void testParseSingleParagraph() {
        List<TextElement> result = parser.parse("Single paragraph.");
        assertEquals(result.size(), 1);

        TextElement paragraph = result.get(0);
        assertEquals(paragraph.getElementType(), ElementType.PARAGRAPH);
    }

    @Test
    public void testParseMultipleParagraphs() {
        String text = "First paragraph.\n\nSecond paragraph.";
        List<TextElement> result = parser.parse(text);
        assertEquals(result.size(), 2);

        assertEquals(result.get(0).getElementType(), ElementType.PARAGRAPH);
        assertEquals(result.get(1).getElementType(), ElementType.PARAGRAPH);
    }

    @Test
    public void testParseWithTabIndentation() {
        String text = "First paragraph.\n\t\tIndented paragraph.";
        List<TextElement> result = parser.parse(text);
        assertEquals(result.size(), 2);
    }

    @Test
    public void testParseEmpty() {
        List<TextElement> result = parser.parse("");
        assertEquals(result.size(), 0);
    }
}