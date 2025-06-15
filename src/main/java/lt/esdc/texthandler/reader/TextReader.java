package lt.esdc.texthandler.reader;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class TextReader {
    private static final Logger logger = LogManager.getLogger(TextReader.class);

    public Optional<String> readText(Path filePath) {
        if (!Files.exists(filePath)) {
            logger.warn("File not found: {}", filePath.toString());
            return Optional.empty();
        }

        try {
            String content = Files.readString(filePath);
            content = content.replaceAll("\\r\\n", "\n");
            logger.info("Successfully read file: {}", filePath.toString());
            logger.info("File content preview:\n" + content.replace("\n", "\\n").replace("\r", "\\r"));
            return Optional.of(content);
        } catch (IOException e) {
            logger.error("Error reading file: {} - {}", filePath.toString(), e.getMessage());
            return Optional.empty();
        }
    }
}
