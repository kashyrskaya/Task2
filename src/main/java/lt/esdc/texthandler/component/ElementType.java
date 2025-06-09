package main.java.lt.esdc.texthandler.component;

public enum ElementType {
    TEXT ("\n\n"),
    PARAGRAPH ("\n"),
    SENTENCE (" "),
    LEXEME (""),
    LETTER (""),
    PUNCTUATION ("");

    private final String delimiter;
    ElementType(String delimiter) {
        this.delimiter = delimiter;
    }
    public String getDelimiter() {
        return delimiter;
    }
}
