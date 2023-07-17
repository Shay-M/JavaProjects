package shay.finalproject.webcraeler.crawlers.limiter;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import shay.finalproject.webcraeler.annotations.Dfs;
import shay.finalproject.webcraeler.config.CrawlerConfig;
import shay.finalproject.webcraeler.crawlers.interfaces.VisitedLinkTracker;
import shay.finalproject.webcraeler.crawlers.limiter.interfaces.AbstractLimiter;

/**
 * The DfsLimiter class is responsible for limiting the crawling process based on the Depth-First Search (DFS) strategy.
 */
@Dfs
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)

public class DfsLimiter extends AbstractLimiter {
    public DfsLimiter(final CrawlerConfig config) {
        super(config);
    }

    @Override
    public boolean continueProcessing(final VisitedLinkTracker urlsToVisit, final long numPagesVisited, final int numOfChildrenInLayer) {
        if (m_depth == m_config.getMaxDepth()) {
            --m_depth;
        }
        else {
            ++m_depth;
        }

        // System.out.println("Depth: " + m_depth + " | Num Pages Visited: " + numPagesVisited);

        return !urlsToVisit.isEmpty() &&
                (noMaxDepthSet || m_depth <= m_config.getMaxDepth()) &&
                (noMaxPagesSet || numPagesVisited < m_config.getMaxPages());
    }
}
