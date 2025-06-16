package lt.esdc.texthandler.app;

import lt.esdc.texthandler.component.TextElement;
import lt.esdc.texthandler.reader.TextReader;
import lt.esdc.texthandler.parser.impl.TextParser;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        Path filePath = Path.of("src/main/resources/sampleTwo.txt");
        TextReader reader = new TextReader();

        Optional<String> fileContent = reader.readText(filePath);

        if (fileContent.isEmpty()) {
            System.err.println("Failed to read the file or the file is empty.");
            return;
        }

        String sampleText = fileContent.get();

        System.out.println("Original text:");
        System.out.println(sampleText);
        System.out.println("\n" + "=".repeat(50) + "\n");

        TextParser parser = new TextParser();
        List<TextElement> parsedText = parser.parse(sampleText);

        System.out.println("Parsed and processed text:");
        for (TextElement textElement : parsedText) {
            System.out.println(textElement.toString());
        }

        System.out.println("\n" + "=".repeat(50) + "\n");
    }
}