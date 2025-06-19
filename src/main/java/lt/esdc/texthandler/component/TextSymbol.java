package lt.esdc.texthandler.component;

import java.util.List;

public class TextSymbol implements TextElement {
    private final char symbol;
    private final ElementType elementType;

    public TextSymbol(char symbol, ElementType elementType) {
        this.symbol = symbol;
        this.elementType = elementType;
    }

    @Override
    public boolean add(TextElement textElement) {
        throw new UnsupportedOperationException("Cannot add leaf element");
    }

    @Override
    public boolean remove(TextElement textElement) {
        throw new UnsupportedOperationException("Cannot remove leaf element");
    }

    @Override
    public List<TextElement> getChild() {
        throw new UnsupportedOperationException("Leaf element has no children");
    }

    @Override
    public ElementType getElementType() {
        return elementType;
    }

    @Override
    public String toString() {
        return String.valueOf(symbol);
    }
}
