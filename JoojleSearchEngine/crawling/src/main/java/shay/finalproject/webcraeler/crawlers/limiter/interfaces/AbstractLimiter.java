package shay.finalproject.webcraeler.crawlers.limiter.interfaces;

import lombok.Getter;
import shay.finalproject.webcraeler.config.CrawlerConfig;

public abstract class AbstractLimiter implements Limiter {

    protected final CrawlerConfig m_config;
    protected final boolean noMaxDepthSet;
    protected final boolean noMaxPagesSet;

    protected int m_depth;
    // protected int m_numPagesVisited;
    protected int m_depthJumper;

    // private AtomicInteger m_depth = new AtomicInteger(0);
    // int currentDepth = m_depth.incrementAndGet();


    public AbstractLimiter(final CrawlerConfig config) {
        m_config = config;

        noMaxDepthSet = m_config.getMaxDepth() == -1;
        noMaxPagesSet = m_config.getMaxPages() == -1;

        m_depth = 0;
        // m_numPagesVisited = 0;
        m_depthJumper = 1;


    }

    @Override
    public int getDepth() {
        return m_depth;
    }
}
