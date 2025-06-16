package lt.esdc.texthandler.service.impl;

import lt.esdc.texthandler.component.TextElement;
import lt.esdc.texthandler.parser.impl.TextParser;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

public class CountVowelConsonantTest {
    private Map<TextElement, CountVowelConsonant.VowelConsonantCount> result;

    @BeforeMethod
    public void setUp() {
        String text = "Hello world! This is a test. \n" +
                "Hello again, world. Testing, one, two, three. This test is simple.\n";

        TextElement parsedText = new TextParser().parse(text).get(0);
        CountVowelConsonant counter = new CountVowelConsonant();
        result = counter.execute(parsedText);
    }

    @Test
    public void testCountOnSentences() {
        Assert.assertEquals(result.size(), 5, "Number of sentences mismatch");

        for (Map.Entry<TextElement, CountVowelConsonant.VowelConsonantCount> entry : result.entrySet()) {
            String sentenceText = entry.getKey().toString().trim();
            CountVowelConsonant.VowelConsonantCount count = entry.getValue();

            switch (sentenceText) {
                case "Hello world!":
                    Assert.assertEquals(count.vowels(), 3);
                    Assert.assertEquals(count.consonants(), 7);
                    break;

                case "This is a test.":
                    Assert.assertEquals(count.vowels(), 4);
                    Assert.assertEquals(count.consonants(), 7);
                    break;

                case "Hello again, world.":
                    Assert.assertEquals(count.vowels(), 6);
                    Assert.assertEquals(count.consonants(), 9);
                    break;

                case "Testing, one, two, three.":
                    Assert.assertEquals(count.vowels(), 7);
                    Assert.assertEquals(count.consonants(), 11);
                    break;

                case "This test is simple.":
                    Assert.assertEquals(count.vowels(), 5);
                    Assert.assertEquals(count.consonants(), 11);
                    break;

                case "":
                    break;

                default:
                    Assert.fail("Unexpected sentence: " + sentenceText);
            }
        }
    }
}
