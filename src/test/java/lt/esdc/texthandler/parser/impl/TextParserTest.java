package lt.esdc.texthandler.parser.impl;

import lt.esdc.texthandler.component.ElementType;
import lt.esdc.texthandler.component.TextElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class TextParserTest {

    private TextParser parser;

    @BeforeMethod
    public void setUp() {
        parser = new TextParser();
    }

    @Test
    public void testParseText() {
        String text = "First paragraph.\n\nSecond paragraph.";
        List<TextElement> result = parser.parse(text);

        assertEquals(result.size(), 1);

        TextElement textElement = result.get(0);
        assertEquals(textElement.getElementType(), ElementType.TEXT);
        assertEquals(textElement.getChild().size(), 2); // 2 paragraphs
    }

    @Test
    public void testParseEmptyText() {
        List<TextElement> result = parser.parse("");
        assertEquals(result.size(), 1);

        TextElement textElement = result.get(0);
        assertEquals(textElement.getElementType(), ElementType.TEXT);
        assertEquals(textElement.getChild().size(), 0);
    }
}
