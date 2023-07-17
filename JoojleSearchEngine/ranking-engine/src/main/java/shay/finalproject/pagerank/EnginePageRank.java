package shay.finalproject.pagerank;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import shay.finalproject.datastructure.LinkMap;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EnginePageRank {
    private final Map<URI, Double> pageRank = new HashMap<>();
    private static final double dampingFactor = 0.85;
    private static final int maxIterations = 80; // maximum number of iterations for the PageRank algorithm

    public void computePageRank(final LinkMap<URI> linkMap) {
        // int numLinks = linkMap.size();
        // double initialScore = 1.0 / numLinks;
        // // Initialize the PageRank scores to 1.0
        // for (URI link : linkMap.getAllLinks()) {
        //     pageRank.put(link, initialScore);
        // }
        // // Iteratively compute the PageRank scores
        // for (int i = 0; i < maxIterations; i++) {
        //     Map<URI, Double> newPageRank = new HashMap<>();
        //     double totalScore = 0.0;
        //     for (URI link : linkMap.getAllLinks()) {
        //         double score = 0.0;
        //         for (URI src : linkMap.getSources(link)) {
        //             int numOutlinks = linkMap.getDestinations(src).size();
        //             double outlinkScore = pageRank.get(src) / numOutlinks;
        //             score += outlinkScore;
        //         }
        //         score = dampingFactor * score + (1 - dampingFactor) * initialScore;
        //         newPageRank.put(link, score);
        //         totalScore += score;
        //     }
        //     // Normalize the PageRank scores
        //     for (URI link : newPageRank.keySet()) {
        //         double score = newPageRank.get(link) / totalScore;
        //         pageRank.put(link, score);
        //     }
        // }
    }
}
