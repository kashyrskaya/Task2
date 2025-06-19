package lt.esdc.texthandler.component;

public enum ElementType {
    TEXT("\n"),
    PARAGRAPH(" "),
    SENTENCE(" "),
    LEXEME(""),
    LETTER(""),
    PUNCTUATION(""),
    SYMBOL("");

    private final String delimiter;

    ElementType(String delimiter) {
        this.delimiter = delimiter;
    }

    public String getDelimiter() {
        return delimiter;
    }
}
