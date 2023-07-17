package shay.finalproject.pagerank;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
// @Primary
public class RankByCount<T> implements PageRankService<T> {
    @Override
    public Map<T, Double> calculatePageRanks(final List<T> items, final URI link) {
        final Map<T, Double> wordCounts = new ConcurrentHashMap<>();

        for (T item : items) {
            wordCounts.put(item, wordCounts.getOrDefault(item, 0.0) + 1);
        }
        return wordCounts;
    }

}

