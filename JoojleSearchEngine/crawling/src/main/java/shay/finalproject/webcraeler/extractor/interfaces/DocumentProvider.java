package shay.finalproject.webcraeler.extractor.interfaces;

import org.jsoup.nodes.Document;

import java.net.URI;
import java.util.Optional;

public interface DocumentProvider {
    Optional<Document> getDocument(final URI link);
}
