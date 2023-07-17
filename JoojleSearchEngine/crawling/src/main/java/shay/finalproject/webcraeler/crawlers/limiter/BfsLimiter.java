package shay.finalproject.webcraeler.crawlers.limiter;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import shay.finalproject.webcraeler.annotations.Bfs;
import shay.finalproject.webcraeler.config.CrawlerConfig;
import shay.finalproject.webcraeler.crawlers.interfaces.VisitedLinkTracker;
import shay.finalproject.webcraeler.crawlers.limiter.interfaces.AbstractLimiter;

/**
 * The BfsLimiter class is responsible for limiting the crawling process based on the Breadth-First Search (BFS) strategy.
 */
@Bfs
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)

public class BfsLimiter extends AbstractLimiter {
    public BfsLimiter(final CrawlerConfig config) {
        super(config);
    }

    @Override
    public boolean continueProcessing(final VisitedLinkTracker urlsToVisit, final long numPagesVisited, final int numOfChildrenInLayer) {
        --m_depthJumper;
        if (m_depthJumper == 0) {
            ++m_depth;
            m_depthJumper = numOfChildrenInLayer;
        }
        // System.out.println("Depth: " + m_depth + " | Num Pages Visited: " + numPagesVisited);

        // System.out.println("Extract Links from: " + link + "\t| find: " + urlsFromPage.size() + " links.\t" + "| words: " + wordsInPage.size());
        // System.out.println("Depth: " + m_depth + " | Num Pages Visited: " + numPagesVisited);

        return !urlsToVisit.isEmpty() &&
                (noMaxDepthSet || m_depth <= m_config.getMaxDepth()) &&
                (noMaxPagesSet || numPagesVisited < m_config.getMaxPages());
    }
}
