package lt.esdc.texthandler.parser;

import lt.esdc.texthandler.component.TextElement;

import java.util.List;

public interface Parser {
    List<TextElement> parse(String text);
}
