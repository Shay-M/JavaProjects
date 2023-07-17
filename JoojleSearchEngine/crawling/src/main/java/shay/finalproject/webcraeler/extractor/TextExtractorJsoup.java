package shay.finalproject.webcraeler.extractor;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import shay.finalproject.webcraeler.extractor.interfaces.TextExtractor;

import java.util.Objects;

@Component
public class TextExtractorJsoup implements TextExtractor {

    @Override
    public String extractTextToBigString(final Document doc) {
        Objects.requireNonNull(doc);
        return doc.body().text();
    }


}
