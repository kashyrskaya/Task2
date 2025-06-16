package lt.esdc.texthandler.component;

import java.util.ArrayList;
import java.util.List;

public class TextComposite implements TextElement {
    private final ElementType elementType;
    private final List<TextElement> elements;

    public TextComposite(ElementType type) {
        this.elementType = type;
        this.elements = new ArrayList<>();
    }

    @Override
    public boolean add(TextElement textElement) {
        return elements.add(textElement);
    }

    @Override
    public boolean remove(TextElement textElement) {
        return elements.remove(textElement);
    }

    @Override
    public List<TextElement> getChild() {
        return elements;
    }

    @Override
    public ElementType getElementType() {
        return elementType;
    }

    @Override
    public String toString() {
        if (elements.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < elements.size(); i++) {
            TextElement element = elements.get(i);
            sb.append(element.toString());

            if (i < elements.size() - 1) {
                sb.append(elementType.getDelimiter());
            }
        }

        return sb.toString();
    }
}
