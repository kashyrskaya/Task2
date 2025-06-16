package lt.esdc.texthandler.service.impl;

import lt.esdc.texthandler.component.TextElement;
import lt.esdc.texthandler.parser.impl.TextParser;
import lt.esdc.texthandler.service.TextService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class RemoveSentencesByWordCountTest {
    private TextService<TextElement> remover;
    private TextElement parsedText;

    @BeforeMethod
    public void setUp() {
        String text = "This is a long enough sentence.\n" +
                "Too short.\n" +
                "Another valid sentence with enough words.";

        TextParser parser = new TextParser();
        parsedText = parser.parse(text).get(0);
    }

    @Test
    public void testRemovesShortSentences() {
        int minWords = 4;
        remover = new RemoveSentencesByWordCount(minWords);
        TextElement result = remover.execute(parsedText);

        assertNotNull(result, "Result should not be null");
        String resultText = result.toString();

        System.out.println("Filtered text: " + resultText);

        assertTrue(resultText.contains("This is a long enough sentence"), "First valid sentence should remain");
        assertTrue(resultText.contains("Another valid sentence with enough words"), "Second valid sentence should remain");
        assertFalse(resultText.contains("Too short"), "Short sentence should be removed");
    }

    @Test
    public void testAllSentencesRemoved() {
        int minWords = 10;
        remover = new RemoveSentencesByWordCount(minWords);

        TextElement result = remover.execute(parsedText);

        assertNull(result, "Should return null when all sentences are removed");
    }
}
