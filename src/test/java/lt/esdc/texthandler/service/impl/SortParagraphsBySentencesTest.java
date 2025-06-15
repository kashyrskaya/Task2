package lt.esdc.texthandler.service.impl;

import lt.esdc.texthandler.component.ElementType;
import lt.esdc.texthandler.component.TextElement;
import lt.esdc.texthandler.parser.impl.TextParser;
import lt.esdc.texthandler.service.TextService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class SortParagraphsBySentencesTest {
    private TextService<TextElement> sorter;
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

        sorter = new SortParagraphsBySentences();
    }

    @Test
    public void testSortParagraphsBySentenceCount() {
        assertEquals(parsedText.getElementType(), ElementType.TEXT);

        TextElement sorted = sorter.execute(parsedText);

        List<TextElement> paragraphs = sorted.getChild();
        assertEquals(paragraphs.size(), 3);

        int previousSentenceCount = -1;
        for (TextElement paragraph : paragraphs) {
            int currentCount = paragraph.getChild().size();
            assertTrue(currentCount >= previousSentenceCount,
                    "Paragraphs not sorted properly: current " + currentCount + " < previous " + previousSentenceCount);
            previousSentenceCount = currentCount;
        }
    }

    @Test
    public void testInputNotModifiedIfNotTextType() {
        TextElement nonTextElement = parsedText.getChild().get(0);

        TextElement result = sorter.execute(nonTextElement);

        assertSame(result, nonTextElement, "Non-TEXT element should be returned unchanged");
    }
}
