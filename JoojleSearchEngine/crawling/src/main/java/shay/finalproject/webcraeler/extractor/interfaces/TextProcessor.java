package shay.finalproject.webcraeler.extractor.interfaces;

import java.util.List;

public interface TextProcessor {

    String cleanText(final String bigString);

    List<String> textToList(final String cleanText);
}
