package shay.finalproject.webcraeler.crawlers.limiter.interfaces;

import shay.finalproject.webcraeler.crawlers.interfaces.VisitedLinkTracker;

public interface Limiter {

    boolean continueProcessing(final VisitedLinkTracker m_urlsToVisit, long numPagesVisited, final int numOfChildrenInlayer);

    int getDepth();

}
