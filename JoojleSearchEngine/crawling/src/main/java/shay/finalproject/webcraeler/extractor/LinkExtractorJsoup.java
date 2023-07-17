package shay.finalproject.webcraeler.extractor;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import shay.finalproject.webcraeler.extractor.interfaces.LinkExtractor;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The LinkExtractorJsoup class is responsible for extracting links from an HTML document using Jsoup library.
 * It implements the LinkExtractor interface.
 */
@Component
public class LinkExtractorJsoup implements LinkExtractor {
    private static final String A_HREF = "a[href]"; // selector for anchor (<a>) elements
    private static final String ABSOLUTE_HREF = "abs:href";

    public List<URI> extractLinks(final Document doc) {
        try {
            final Elements elements = doc.select(A_HREF);
            final String baseUri = doc.baseUri();
            final List<URI> links = new ArrayList<>();

            for (Element link : elements) {
                String url = link.attr(ABSOLUTE_HREF);
                if (isValidUrl(url)) {
                    url = sanitizeUrl(url);
                    final URI uri = resolveUrl(url, baseUri);
                    if (uri != null) {
                        links.add(uri);
                    }
                }
            }

            return links;
        }
        catch (IllegalStateException ex) {
            System.err.println("*** Error occurred during link extraction: " + ex.getMessage());
            return Collections.emptyList();
        }
    }

    private boolean isValidUrl(final String url) {
        return !url.isEmpty() && (url.startsWith("http://") || url.startsWith("https://"));
    }

    /**
     * Sanitizes the given URL by encoding special characters.
     */
    private String sanitizeUrl(String url) {
        if (url.contains("#")) {
            url = UrlEncoder.encodeUrl(url);
        }
        return url;
    }

    /**
     * Resolves the given URL against the base URI of the document.
     *
     * @return The resolved URI, or null if an error occurs during URI creation.
     */
    private URI resolveUrl(final String url, final String baseUri) {
        try {
            URI uri = new URI(url);
            if (!uri.isAbsolute()) {
                uri = new URI(baseUri).resolve(uri);
            }
            return uri;
        }
        catch (URISyntaxException | IllegalArgumentException ex) {
            System.err.println("*** Error occurred during URL resolution: " + ex.getMessage());
            return null;
        }
    }
}
