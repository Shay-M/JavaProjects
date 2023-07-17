package shay.finalproject.webcraeler.extractor;

import org.springframework.stereotype.Component;
import shay.finalproject.webcraeler.extractor.interfaces.TextProcessor;

import java.util.List;
import java.util.Objects;

/**
 * The WebTextProcessor class is responsible for cleaning and processing text data.
 */
@Component
public class WebTextProcessor implements TextProcessor {
    private static final String REGEX_PUNCTUATION = "[^a-z0-9'`\\s]";
    private static final String REGEX_SPACES = "\\s+";
    private static final String SPACE = " ";

    @Override
    public String cleanText(final String bigString) {
        Objects.requireNonNull(bigString, "bigString must not be null");
        return bigString
                .toLowerCase()
                .replaceAll(REGEX_PUNCTUATION, SPACE)
                .replaceAll(REGEX_SPACES, SPACE)
                .trim();
    }

    @Override
    public List<String> textToList(final String cleanText) {
        Objects.requireNonNull(cleanText, "cleanText must not be null");
        return List.of(cleanText.split(SPACE));
    }
}
