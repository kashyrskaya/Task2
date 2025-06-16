package lt.esdc.texthandler.service.impl;

import lt.esdc.texthandler.component.ElementType;
import lt.esdc.texthandler.component.TextElement;
import lt.esdc.texthandler.parser.impl.TextParser;
import lt.esdc.texthandler.service.TextService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class FindSentenceWithLongestWordTest {
    private TextService<TextElement> finder;
    private TextElement parsedText;

    @BeforeMethod
    public void setUp() {
        String text = "This is the first sentence. This is the second one.\n" +
                "\n" +
                "Short paragraph.\n" +
                "\n" +
                "Another paragraph. With two sentences.";

        TextParser parser = new TextParser();
        parsedText = parser.parse(text).get(0);

        finder = new FindSentenceWithLongestWord();
    }

    @Test
    public void testFindSentenceWithLongestWord() {
        TextElement result = finder.execute(parsedText);

        assertNotNull(result, "Result should not be null");
        assertEquals(result.getElementType(), ElementType.SENTENCE, "Result should be a sentence");

        String sentenceText = result.toString();
        System.out.println("Found sentence: " + sentenceText);

        assertTrue(sentenceText.contains("paragraph"), "Sentence should contain the word 'paragraph'");
        assertTrue(sentenceText.startsWith("Short"), "Should return the first matching sentence");
    }
}
