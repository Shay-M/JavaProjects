package shay.finalproject.webcraeler.crawlers;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import shay.finalproject.controller.infrastructure.Statistics;
import shay.finalproject.controller.infrastructure.StatisticsService;
import shay.finalproject.webcraeler.config.CrawlerConfig;
import shay.finalproject.webcraeler.crawlers.interfaces.Crawler;
import shay.finalproject.webcraeler.crawlers.interfaces.VisitedLinkTracker;
import shay.finalproject.webcraeler.crawlers.limiter.interfaces.Limiter;
import shay.finalproject.webcraeler.crawlers.linktracker.BundleIndexer;
import shay.finalproject.webcraeler.event.DataReadyEventPublisher;
import shay.finalproject.webcraeler.extractor.interfaces.DocumentProvider;
import shay.finalproject.webcraeler.extractor.interfaces.LinkExtractor;
import shay.finalproject.webcraeler.extractor.interfaces.TextExtractor;
import shay.finalproject.webcraeler.extractor.interfaces.TextProcessor;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The WebCrawler class represents a web crawler component that crawls web pages and extracts information.
 * It implements the Crawler interface and provides functionality for crawling web pages asynchronously.
 */
@Service
@Configuration
@EnableScheduling
@EnableAsync
public class WebCrawler implements Crawler, StatisticsService {
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    private final TextExtractor m_textExtractor;
    private final TextProcessor m_cleanTextToString;

    private final CrawlerConfig m_config;
    private final Set<URI> m_visitedUrls = ConcurrentHashMap.newKeySet();
    private final VisitedLinkTracker m_urlsToVisit;
    private final Map<URI, Map<URI, Long>> m_linksMap = new ConcurrentHashMap<>();
    private final LinkExtractor m_linkExtractor;
    private final DocumentProvider m_documentProvider;

    private final BundleIndexer m_bundleIndexer;
    private final Limiter m_limiter;
    private final DataReadyEventPublisher m_publisher;
    private boolean inProgress = false;


    private Instant m_startTime;
    private long m_duration;

    public WebCrawler(final CrawlerConfig config,
                      final VisitedLinkTracker urlsToVisit,
                      final LinkExtractor linkExtractor,
                      final TextExtractor textExtractor,
                      final TextProcessor cleanTextToString,
                      final DocumentProvider documentProvider,
                      final BundleIndexer bundleIndexer,
                      final Limiter limiter,
                      final DataReadyEventPublisher publisher) {
        m_config = config;
        m_urlsToVisit = urlsToVisit;
        m_linkExtractor = linkExtractor;
        m_textExtractor = textExtractor;
        m_cleanTextToString = cleanTextToString;
        m_documentProvider = documentProvider;
        m_bundleIndexer = bundleIndexer;
        m_limiter = limiter;
        m_publisher = publisher;
    }

    @Override
    // @Scheduled(fixedRate = 100_000)
    @Async
    public void crawl() {
        Objects.requireNonNull(m_config.getStartUrl());
        m_urlsToVisit.add(URI.create(m_config.getStartUrl()));
        inProgress = true;
        m_startTime = Instant.now();
        // System.out.println("Thread: " + Thread.currentThread().getName());

        var numOfChildrenInLayer = 1;
        while (m_limiter.continueProcessing(m_urlsToVisit, m_visitedUrls.size(), numOfChildrenInLayer)) {
            final URI link = m_urlsToVisit.poll();
            if (link == null || m_visitedUrls.contains(link)) {
                continue;
            }
            final Optional<Document> doc = m_documentProvider.getDocument(link);
            if (doc.isEmpty()) {
                continue;
            }
            // System.out.println(Thread.currentThread().getName() + "got: "+ link);
            m_visitedUrls.add(link);

            final var urlsFromPage = m_linkExtractor.extractLinks(doc.get());
            // doc.get().title();
            m_bundleIndexer.links().update(urlsFromPage, link);
            numOfChildrenInLayer = urlsFromPage.size();

            final var nanDuplicate = Set.copyOf(urlsFromPage);
            m_urlsToVisit.addAll(nanDuplicate);

            final var wordsInPage = extractedTextToWords(doc.get());
            m_bundleIndexer.words().update(wordsInPage, link); // url,
        }
        endCrawl();
    }


    public void endCrawl() {
        System.out.println("Crawling finished! , take: " + getCrawlDuration() + " Seconds.");
        inProgress = false;
        m_publisher.publishCustomEvent(m_visitedUrls.size(), -1, -1); // todo numLink and num ignored link
        // o When this phase is done it will print a message with the following information:
        // ■ count of unique pages visited.
        // ■ Total number of links encountered discovered. (Counting duplicates)
        // ■ Total number of links on visited pages which get ignored as they link to site outside the crawl group.
    }

    protected List<String> extractedTextToWords(final Document page) {
        final var BigString = m_textExtractor.extractTextToBigString(page);
        final var cleanBigString = m_cleanTextToString.cleanText(BigString);

        return m_cleanTextToString.textToList(cleanBigString);
    }

    @Override
    public Statistics getStatistics() {
        return new Statistics(inProgress, m_urlsToVisit.size(), m_visitedUrls.size(), m_limiter.getDepth(), getCrawlDuration());
    }

    private long getCrawlDuration() {
        if (inProgress) {
            final Instant endTime = Instant.now();
            m_duration = Duration.between(m_startTime, endTime).toSeconds();
        }

        return m_duration;
    }
}
