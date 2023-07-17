// package shay.finalproject.pagerank;
//
// import org.springframework.context.annotation.Primary;
// import org.springframework.stereotype.Component;
// import shay.finalproject.datastructure.LinkMap;
// import shay.finalproject.datastructure.infrastructure.LinkWordCount;
//
// import java.net.URI;
// import java.util.*;
// import java.util.concurrent.ConcurrentHashMap;
//
//
// // @Component
// // @Primary
// // public class RankByAlgorithm<T> implements PageRankService<T> {
// //     private static final int MAX_ITERATIONS = 4;
// //     private static final double INITIAL_PAGE_RANK = 1.0;
// //
// //     public Map<T, Double> computePageRank(final List<T> pages) {
// //         Map<T, List<T>> graph = new HashMap<>();
// //         for (T page : pages) {
// //             graph.put(page, new ArrayList<>());
// //         }
// //
// //         // Populate graph with links between pages (you will need to implement this logic)
// //         // ...
// //
// //         Map<T, Double> ranks = new HashMap<>();
// //         for (T page : graph.keySet()) {
// //             ranks.put(page, INITIAL_PAGE_RANK);
// //         }
// //
// //         // Step 2: Compute the flow of ranks using iterative algorithm
// //         for (int i = 0; i < MAX_ITERATIONS; i++) {
// //             Map<T, Double> newRanks = new HashMap<>();
// //             double dampingFactor = 0.85; // recommended damping factor by Google
// //             double sum = 0.0;
// //             for (T page : graph.keySet()) {
// //                 double rank = 0.0;
// //                 for (T source : graph.keySet()) {
// //                     if (graph.get(source).contains(page)) {
// //                         rank += ranks.get(source) / graph.get(source).size();
// //                     }
// //                 }
// //                 rank = (1 - dampingFactor) + dampingFactor * rank;
// //                 newRanks.put(page, rank);
// //                 sum += rank;
// //             }
// //             ranks = newRanks;
// //
// //             // Check for convergence: If the change in sum of ranks is negligible, break out of loop
// //             double delta = 0.0001; // small threshold for convergence
// //             if (Math.abs(sum - graph.size()) < delta) {
// //                 break;
// //             }
// //         }
// //
// //         // Step 3: Return the final page ranks
// //         return ranks;
// //     }
//
//
// // @Component
// // @Primary
// // // @Service
// // public class RankByAlgorithm<T> implements PageRankService<T> {
// //     private static final int MAX_ITERATIONS = 4;
// //     private static final double INITIAL_PAGE_RANK = 1.0;
// //
// //     private final Map<URI, Double> pageRanks = new ConcurrentHashMap<>();
// //     private final Map<URI, Double> newRanks = new ConcurrentHashMap<>();
// //
// //     @Override
// //     public Map<T, Double> calculatePageRanks(final List<T> items, final URI link) {
// //
// //         pageRanks.replaceAll((p, v) -> INITIAL_PAGE_RANK);
// //
// //         int t = 0;
// //         while (t < MAX_ITERATIONS) {
// //             newRanks.clear();
// //
// //             // calculate the flow of ranks for each page
// //             linkMap.parallelStream().forEach(word -> {
// //                 List<LinkWordCount> linkWordCounts = linkMap.getLinkWordCount(word);
// //
// //                 if (linkWordCounts == null) {
// //                     return;
// //                 }
// //
// //                 linkWordCounts.forEach(linkWordCount -> {
// //                     URI page = linkWordCount.url();
// //                     double rank = pageRanks.getOrDefault(page, 0.0);
// //
// //                     List<LinkWordCount> outboundLinks = linkMap.getLinkWordCount(page);
// //                     if (outboundLinks == null || outboundLinks.isEmpty()) {
// //                         // if a page has no outbound links, then distribute its rank evenly to all other pages
// //                         linkMap.getWords().forEach(w -> {
// //                             double newRank = newRanks.getOrDefault(w, 0.0) + (rank / linkMap.size());
// //                             newRanks.put(w, newRank);
// //                         });
// //                     }
// //                     else {
// //                         // distribute the rank proportionally to the outbound links
// //                         outboundLinks.forEach(outLink -> {
// //                             double newRank = newRanks.getOrDefault(outLink.url(), 0.0) +
// //                                     (rank * outLink.count() / getTotalCount(outboundLinks));
// //                             newRanks.put(outLink.url(), newRank);
// //                         });
// //                     }
// //                 });
// //             });
// //
// //             // update page ranks
// //             pageRanks.putAll(newRanks);
// //             t++;
// //         }
// //
// //         return pageRanks;
// //     }
// //
// //     // helper method to get the total count of links
// //     private double getTotalCount(List<LinkWordCount> links) {
// //         return links.stream().mapToDouble(LinkWordCount::count).sum();
// //     }
//
// public class PageRankCalculator<T> {
//     private static final int MAX_ITERATIONS = 8;
//     private static final double DAMPING_FACTOR = 0.85;
//     private final LinkMap<T> linkMap;
//
//     public PageRankCalculator(final LinkMap<T> linkMap) {
//         this.linkMap = linkMap;
//     }
//
//     public Map<T, Double> calculatePageRanks(final List<T> items, final URI link) {
//         // Initialize page ranks to 1.0
//         final Map<T, Double> pageRanks = new HashMap<>();
//
//         for (T item : items) {
//             pageRanks.put(item, 1.0);
//         }
//
//         // Build the transition matrix
//         Map<T, List<T>> outgoingLinks = new HashMap<>();
//         for (T item : items) {
//             List<LinkWordCount> linkWordCounts = linkMap.getLinkWordCount(item);
//             List<T> links = new ArrayList<>();
//             for (LinkWordCount linkWordCount : linkWordCounts) {
//                 links.add((T) linkWordCount.url());
//             }
//             outgoingLinks.put(item, links);
//         }
//         double[][] transitionMatrix = buildTransitionMatrix(items, outgoingLinks);
//
//         // Perform the PageRank calculation
//         for (int i = 0; i < MAX_ITERATIONS; i++) {
//             double[] previousPageRanks = Arrays.copyOf(pageRanks.values().toArray(), pageRanks.size(), double[].class);
//             for (int j = 0; j < items.size(); j++) {
//                 double sum = 0.0;
//                 for (int k = 0; k < items.size(); k++) {
//                     if (transitionMatrix[k][j] != 0) {
//                         sum += previousPageRanks[k] / outgoingLinks.get(items.get(k)).size();
//                     }
//                 }
//                 double newPageRank = (1 - DAMPING_FACTOR) + DAMPING_FACTOR * sum;
//                 pageRanks.put(items.get(j), newPageRank);
//             }
//         }
//
//         // Return the final page ranks
//         return pageRanks;
//     }
//
//     private double[][] buildTransitionMatrix(final List<T> items, final Map<T, List<T>> outgoingLinks) {
//         double[][] transitionMatrix = new double[items.size()][items.size()];
//         for (int i = 0; i < items.size(); i++) {
//             T item = items.get(i);
//             List<T> links = outgoingLinks.get(item);
//             if (links != null && !links.isEmpty()) {
//                 double weight = 1.0 / links.size();
//                 for (T link : links) {
//                     int j = items.indexOf(link);
//                     if (j >= 0) {
//                         transitionMatrix[j][i] = weight;
//                     }
//                 }
//             }
//             else {
//                 double weight = 1.0 / items.size();
//                 for (int j = 0; j < items.size(); j++) {
//                     transitionMatrix[j][i] = weight;
//                 }
//             }
//         }
//         return transitionMatrix;
//     }
// }