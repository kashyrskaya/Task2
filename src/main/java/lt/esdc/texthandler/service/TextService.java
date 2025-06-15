package lt.esdc.texthandler.service;

import lt.esdc.texthandler.component.TextElement;

public interface TextService<T> {
    T execute(TextElement textElement);
}
