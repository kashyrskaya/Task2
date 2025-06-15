package lt.esdc.texthandler.service.impl;

import lt.esdc.texthandler.comparator.BySentenceCountComparator;
import lt.esdc.texthandler.component.ElementType;
import lt.esdc.texthandler.component.TextComposite;
import lt.esdc.texthandler.component.TextElement;
import lt.esdc.texthandler.service.TextService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class SortParagraphsBySentences implements TextService<TextElement> {
    private static final Logger logger = LogManager.getLogger(SortParagraphsBySentences.class);

    @Override
    public TextElement execute(TextElement textElement) {
        if (textElement.getElementType() != ElementType.TEXT) {
            logger.warn("Invalid element type: {}", textElement.getElementType());
            return textElement;
        }

        List<TextElement> paragraphs = new ArrayList<>(textElement.getChild());

        paragraphs.sort(new BySentenceCountComparator());

        TextElement sortedText = new TextComposite(ElementType.TEXT);
        for (TextElement paragraph : paragraphs) {
            sortedText.add(paragraph);
        }

        return sortedText;
    }
}
