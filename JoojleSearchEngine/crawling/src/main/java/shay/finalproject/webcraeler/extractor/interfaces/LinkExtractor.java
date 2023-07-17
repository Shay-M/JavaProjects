package shay.finalproject.webcraeler.extractor.interfaces;

import org.jsoup.nodes.Document;

import java.net.URI;
import java.util.List;
import java.util.Set;

public interface LinkExtractor {
    List<URI> extractLinks(final Document doc);
}
