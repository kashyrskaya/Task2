package lt.esdc.texthandler.component;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import static org.testng.Assert.*;
import java.util.List;

public class TextCompositeTest {

    private TextComposite textComposite;
    private TextElement child1;
    private TextElement child2;

    @BeforeMethod
    public void setUp() {
        textComposite = new TextComposite(ElementType.PARAGRAPH);
        child1 = new TextSymbol("A", ElementType.LETTER);
        child2 = new TextSymbol("B", ElementType.LETTER);
    }

    @Test
    public void testConstructor() {
        assertEquals(textComposite.getElementType(), ElementType.PARAGRAPH);
        assertTrue(textComposite.getChild().isEmpty());
    }

    @Test
    public void testAdd() {
        assertTrue(textComposite.add(child1));
        assertEquals(textComposite.getChild().size(), 1);
        assertEquals(textComposite.getChild().get(0), child1);
    }

    @Test
    public void testAddMultiple() {
        assertTrue(textComposite.add(child1));
        assertTrue(textComposite.add(child2));
        assertEquals(textComposite.getChild().size(), 2);
    }

    @Test
    public void testRemove() {
        textComposite.add(child1);
        textComposite.add(child2);

        assertTrue(textComposite.remove(child1));
        assertEquals(textComposite.getChild().size(), 1);
        assertEquals(textComposite.getChild().get(0), child2);
    }

    @Test
    public void testRemoveNonExistent() {
        textComposite.add(child1);
        assertFalse(textComposite.remove(child2));
        assertEquals(textComposite.getChild().size(), 1);
    }

    @Test
    public void testGetChild() {
        textComposite.add(child1);
        textComposite.add(child2);

        List<TextElement> children = textComposite.getChild();
        assertEquals(children.size(), 2);
        assertEquals(children.get(0), child1);
        assertEquals(children.get(1), child2);
    }

    @Test
    public void testToStringEmpty() {
        assertEquals(textComposite.toString(), "");
    }

    @Test
    public void testToStringSingleElement() {
        textComposite.add(child1);
        assertEquals(textComposite.toString(), "A");
    }

    @Test
    public void testToStringMultipleElements() {
        textComposite.add(child1);
        textComposite.add(child2);
        assertEquals(textComposite.toString(), "A B"); // PARAGRAPH delimiter is " "
    }

    @Test
    public void testToStringWithDifferentDelimiters() {
        TextComposite lexeme = new TextComposite(ElementType.LEXEME);
        lexeme.add(child1);
        lexeme.add(child2);
        assertEquals(lexeme.toString(), "AB"); // LEXEME delimiter is ""

        TextComposite text = new TextComposite(ElementType.TEXT);
        text.add(child1);
        text.add(child2);
        assertEquals(text.toString(), "A\nB"); // TEXT delimiter is "\n"
    }
}