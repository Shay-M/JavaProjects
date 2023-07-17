package shay.finalproject.webcraeler.crawlers.linktracker;

import shay.finalproject.webcraeler.crawlers.interfaces.VisitedLinkTracker;

import java.net.URI;
import java.util.Deque;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;

public abstract class AbstractVisitedTrackerDeque implements VisitedLinkTracker {
    // private final Queue<URI> m_urlsToVisit = new ConcurrentLinkedQueue<>();
    // private final Queue<URI> m_urlsToVisit = new ConcurrentLinkedQueue<>(); //  first-in, first-out
    protected final Deque<URI> m_urlsToVisit = new ConcurrentLinkedDeque<>();

    @Override
    public boolean add(final URI link) {
        return m_urlsToVisit.add(link);
    }

    @Override
    public abstract boolean addAll(Set<URI> links);

    @Override
    public boolean isEmpty() {
        return m_urlsToVisit.isEmpty();
    }

    @Override
    public boolean contains(URI link) {
        return m_urlsToVisit.contains(link);
    }

    @Override
    public URI poll() {
        return m_urlsToVisit.poll();
    }

    @Override
    public int size() {
        return m_urlsToVisit.size();
    }
}
