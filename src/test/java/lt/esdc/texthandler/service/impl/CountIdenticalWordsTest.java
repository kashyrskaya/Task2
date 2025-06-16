package lt.esdc.texthandler.service.impl;

import lt.esdc.texthandler.component.TextElement;
import lt.esdc.texthandler.parser.impl.TextParser;
import lt.esdc.texthandler.service.TextService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.*;

public class CountIdenticalWordsTest {
    private TextService<Map<String, Integer>> counter;
    private TextElement parsedText;

    @BeforeMethod
    public void setUp() {
        String text = "Hello world! This is a test. \n" +
                "Hello again, world. Testing, one, two, three. This test is simple.\n";

        TextParser parser = new TextParser();
        parsedText = parser.parse(text).get(0);

        counter = new CountIdenticalWords();
    }

    @Test
    public void testCountIdenticalWords() {
        Map<String, Integer> result = counter.execute(parsedText);

        assertEquals(result.size(), 5, "Expected 5 words occurring more than once");

        assertTrue(result.containsKey("hello"));
        assertEquals(result.get("hello").intValue(), 2);

        assertTrue(result.containsKey("world"));
        assertEquals(result.get("world").intValue(), 2);

        assertTrue(result.containsKey("this"));
        assertEquals(result.get("this").intValue(), 2);

        assertTrue(result.containsKey("is"));
        assertEquals(result.get("is").intValue(), 2);

        assertTrue(result.containsKey("test"));
        assertEquals(result.get("test").intValue(), 2);

        assertFalse(result.containsKey("again"));
        assertFalse(result.containsKey("testing"));
        assertFalse(result.containsKey("one"));
        assertFalse(result.containsKey("two"));
        assertFalse(result.containsKey("three"));
        assertFalse(result.containsKey("simple"));
    }
}
