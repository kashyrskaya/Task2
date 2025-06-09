package main.java.lt.esdc.texthandler.component;

import java.util.List;

public class TextSymbol implements TextElement {
    private final String symbol;
    private final ElementType elementType;

    public TextSymbol(String symbol, ElementType elementType) {
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
        return symbol;
    }
}
