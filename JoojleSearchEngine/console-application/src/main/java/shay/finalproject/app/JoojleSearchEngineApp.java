package shay.finalproject.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shay.finalproject.search.QuerySearch;
import shay.finalproject.search.api.SearchController;
import shay.finalproject.search.infrastructure.interfaces.QueryResult;
import shay.finalproject.search.infrastructure.interfaces.SearchEngine;
import shay.finalproject.search.infrastructure.interfaces.SupplierQuery;
import shay.finalproject.webcraeler.config.CrawlerConfig;
import shay.finalproject.webcraeler.crawlers.interfaces.Crawler;
import shay.finalproject.webcraeler.event.DataReadyEvent;

import java.util.concurrent.CountDownLatch;

/**
 * The JoojleSearchEngineApp class represents the main application class for the Joojle Search Engine.
 * It initializes and configures the necessary components for the application.
 */
@Component
public class JoojleSearchEngineApp implements CommandLineRunner {
    // CommandLineRunner: which means it will run a specific task when the application starts.
    private final Crawler crawler;
    private final SearchEngine querySearch;
    private final SupplierQuery supplierQuery;
    private final CrawlerConfig config;
    int m_numberOfThread;
    private final CountDownLatch m_crawlingLatch;

    public JoojleSearchEngineApp(final Crawler crawler, final QuerySearch querySearch, final SupplierQuery supplierQuery, final CrawlerConfig config) {
        this.crawler = crawler;
        this.querySearch = querySearch;
        this.supplierQuery = supplierQuery;
        this.config = config;
        m_numberOfThread = config.getNumberOfThread();
        m_crawlingLatch = new CountDownLatch(m_numberOfThread);

        System.out.println(config);
    }

    @Override
    public void run(String... args) {
        System.out.println("start!");
        for (int i = 0; i < m_numberOfThread; ++i) {
            crawler.crawl();
        }

        // querySearch.search("Learn");
        //


        // querySearch.search("Games PlayStation");

        // querySearch.search("spells");
        //
        // System.out.println("");
        // querySearch.search("Learn spells");
        //
        // System.out.println("");
        // querySearch.search("Learn -spells");
        // Count down the latch to indicate crawling is finished


    }

    @EventListener
    public void handleDataReadyEvent(final DataReadyEvent event) throws InterruptedException {
        m_crawlingLatch.countDown();
        System.out.println("Received DataReadyEvent with numUniquePages=" + event.getNumUniquePages()
                + ", numLinks=" + event.getNumLinks() + ", numIgnoredLinks=" + event.getNumIgnoredLinks());
        if (m_crawlingLatch.getCount() == 0) {
            m_crawlingLatch.await();
            allCrawlsFinished();
        }
    }

    private void allCrawlsFinished() {
        System.out.println("All Crawling finished!");
        String queryToSearch;
        do {
            queryToSearch = supplierQuery.getQuery();
            System.out.println(querySearch.search(queryToSearch));
            System.out.println();
        } while (!queryToSearch.isEmpty());
    }

    // @RestController
    // @RequestMapping("/api/search")
    // public class SearchController {
    //
    //     @Autowired
    //     private SearchEngine searchEngine;
    //
    //     @GetMapping("/{query}")
    //     public QueryResult search(@PathVariable String query) {
    //         // List<SearchResult> results = searchEngine.search(query);
    //         var results = searchEngine.search(query);
    //         System.out.println(query);
    //         System.out.println(results);
    //         // Return the search results with a 200 OK status
    //         return results;
    //     }
    //
    // }


}
