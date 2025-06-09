package main.java.lt.esdc.texthandler.parser;

import main.java.lt.esdc.texthandler.component.TextElement;

import java.util.List;

public interface CustomParser {
    List<TextElement> parse(String text);
}
