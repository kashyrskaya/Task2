package lt.esdc.texthandler.parser.impl;

import lt.esdc.texthandler.component.ElementType;
import lt.esdc.texthandler.component.TextElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class SentenceParserTest {

    private SentenceParser parser;

    @BeforeMethod
    public void setUp() {
        parser = new SentenceParser();
    }

    @Test
    public void testParseSingleSentence() {
        List<TextElement> result = parser.parse("Hello world.");
        assertEquals(result.size(), 1);

        TextElement sentence = result.get(0);
        assertEquals(sentence.getElementType(), ElementType.SENTENCE);
        assertEquals(sentence.toString(), "Hello world.");
    }

    @Test
    public void testParseMultipleSentences() {
        List<TextElement> result = parser.parse("First sentence. Second sentence!");
        assertEquals(result.size(), 2);

        assertEquals(result.get(0).toString(), "First sentence.");
        assertEquals(result.get(1).toString(), "Second sentence!");
    }

    @Test
    public void testParseIncomplete() {
        List<TextElement> result = parser.parse("Incomplete sentence");
        assertEquals(result.size(), 1);

        assertEquals(result.get(0).toString(), "Incomplete sentence");
    }
}