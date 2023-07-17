package shay.finalproject.webcraeler.crawlers.interfaces;

import java.net.URI;
import java.util.Collection;
import java.util.Set;

public interface VisitedLinkTracker {

    boolean add(final URI link);

    boolean addAll(final Set<URI> links);

    boolean isEmpty();

    boolean contains(final URI link);

    URI poll();

    int size();
}
