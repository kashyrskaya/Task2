package lt.esdc.texthandler.comparator;

import lt.esdc.texthandler.component.ElementType;
import lt.esdc.texthandler.component.TextElement;

import java.util.Comparator;

public class BySentenceCountComparator implements Comparator<TextElement> {

    @Override
    public int compare(TextElement p1, TextElement p2) {
        int sentences1 = countSentences(p1);
        int sentences2 = countSentences(p2);
        return Integer.compare(sentences1, sentences2);
    }

    private int countSentences(TextElement paragraph) {
        if (paragraph.getElementType() != ElementType.PARAGRAPH) {
            return 0;
        }
        return paragraph.getChild().size();
    }
}
