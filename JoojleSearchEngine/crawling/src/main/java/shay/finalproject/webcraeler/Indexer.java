package shay.finalproject.webcraeler;


import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import shay.finalproject.datastructure.LinkMap;
import shay.finalproject.datastructure.infrastructure.LinkWordCount;
import shay.finalproject.pagerank.PageRankService;

import java.net.URI;
import java.util.List;

/**
 * The Indexer class is responsible for updating and maintaining a LinkMap that associates pages with their corresponding link word counts.
 * It utilizes a PageRankService to calculate page ranks for the provided pages.
 */

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Indexer<T> {
    private final LinkMap<T> linkMap = new LinkMap<T>();
    private final PageRankService<T> pageRankService;

    public Indexer(final PageRankService<T> pageRankService) {
        this.pageRankService = pageRankService;
    }

    public void update(final List<T> pages, final URI link) {
        final var countWords = pageRankService.calculatePageRanks(pages, link);
        for (var item : pages) {
            linkMap.add(item, new LinkWordCount(link, countWords.get(item)));
        }
    }

    // private Map<T, Long> rankByCount(final List<T> items) {
    //     final Map<T, Long> wordCounts = new HashMap<>();
    //     for (T item : items) {
    //         wordCounts.put(item, wordCounts.getOrDefault(item, 0L) + 1);
    //     }
    //     return wordCounts;
    // }

    // // well be remove
    // public void print() {
    //     for (var name : linkMap.getMap().keySet()) {
    //         String key = name.toString();
    //         String value = linkMap.getLinkWordCount(name).toString();
    //         System.out.println(key + " " + value);
    //     }
    //
    // }

    public LinkMap<T> getLinkMap() {
        return linkMap;
    }


}
