package shay.finalproject.webcraeler.extractor.interfaces;

import org.jsoup.nodes.Document;

public interface TextExtractor {

    String extractTextToBigString(final Document doc);

}
