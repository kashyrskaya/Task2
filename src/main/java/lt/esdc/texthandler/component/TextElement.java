package lt.esdc.texthandler.component;

import java.util.List;

public interface TextElement {
    boolean add(TextElement textElement);

    boolean remove(TextElement textElement);

    List<TextElement> getChild();

    ElementType getElementType();

    String toString();
}
