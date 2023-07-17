package shay.finalproject.pagerank;

import java.net.URI;
import java.util.List;
import java.util.Map;

public interface PageRankService<T> {

    Map<T, Double> calculatePageRanks(final List<T> items, final URI link);
    // Map<T, Double> computePageRank(final LinkMap<URI> linkMap);
}
