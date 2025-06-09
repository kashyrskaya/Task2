package main.java.lt.esdc.texthandler.main;

import main.java.lt.esdc.texthandler.component.TextElement;
import main.java.lt.esdc.texthandler.parser.impl.CustomTextParser;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String sampleText = "    This is a first paragraph with calculation 5 + 3.\n" +
                "Second sentence with multiplication 10 * 2. And division 20 / 4.\n\n" +
                "    This is another paragraph with subtraction 15 - 7.\n" +
                "Final sentence with decimal 3.5 + 2.1.";

        System.out.println("Original text:");
        System.out.println(sampleText);
        System.out.println("\n" + "=".repeat(50) + "\n");

        CustomTextParser parser = new CustomTextParser();
        List<TextElement> parsedText = parser.parse(sampleText);

        System.out.println("Parsed and processed text:");
        for (TextElement textElement : parsedText) {
            System.out.println(textElement.toString());
        }

        System.out.println("\n" + "=".repeat(50) + "\n");
    }
}