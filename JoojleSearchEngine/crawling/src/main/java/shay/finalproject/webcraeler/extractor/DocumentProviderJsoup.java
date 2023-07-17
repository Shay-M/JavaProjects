package shay.finalproject.webcraeler.extractor;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import shay.finalproject.webcraeler.extractor.interfaces.DocumentProvider;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Optional;

/**
 * The DocumentProviderJsoup class is responsible for retrieving HTML documents from a given URI using the Jsoup library.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DocumentProviderJsoup implements DocumentProvider {
    private static final int TIME_OUT = 5000;

    @Override
    public Optional<Document> getDocument(final URI link) {
        // if (link == null || m_visitedUrls.contains(link)) {
        //     return Optional.empty();
        // }
        final Document doc = getPageFromLink(link);
        if (doc == null) {
            System.err.println("*** Failed to retrieve document from " + link);
            return Optional.empty();
        }
        return Optional.of(doc);
    }

    @Async
    Document getPageFromLink(final URI link) {
        try {
            final Document page = Jsoup.connect(link.toString()).
                    timeout(TIME_OUT)
                    .followRedirects(false)
                    .userAgent("Mozilla")
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true) // todo sleep
                    .get();
            return page;

        }
        catch (HttpStatusException e) {
            if (e.getStatusCode() == 404) {
                System.err.println("*** Page not found at " + link);
            }
            else {
                System.err.println("*** Failed to load page " + link + ": " + e.getMessage());
            }
        }
        catch (MalformedURLException ex) {
            System.err.println("*** url: " + ex.getMessage());
        }

        catch (IOException ex) {
            System.err.println("*** Failed to load page " + link + ": " + ex.getMessage());
        }
        return null;

    }
}
