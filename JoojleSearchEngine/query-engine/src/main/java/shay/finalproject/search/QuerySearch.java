package shay.finalproject.search;

import org.springframework.stereotype.Component;
import shay.finalproject.datastructure.infrastructure.LinkWordCount;
import shay.finalproject.search.infrastructure.QueryResultFailure;
import shay.finalproject.search.infrastructure.QueryResultSuccess;
import shay.finalproject.search.infrastructure.interfaces.QueryResult;
import shay.finalproject.search.infrastructure.interfaces.SearchEngine;
import shay.finalproject.search.parsers.infrastructure.ProcessedQuery;
import shay.finalproject.search.parsers.QueryPhase;
import shay.finalproject.webcraeler.crawlers.linktracker.BundleIndexer;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The QuerySearch class represents a search engine that performs queries and retrieves search results based on positive and negative terms.
 */
// @Service
@Component
public class QuerySearch implements SearchEngine {
    private static final String ONLY_NEGATIVE_TERMS = "Search query with only negative terms is not allowed!";
    private static final String QUERY_IS_EMPTY = "Query is Empty! ,Minimum length to word is 3!";
    private static final String QUERY_NOT_FOUND = "Query not found!";
    private static final String BOTH_POSITIVE_AND_NEGATIVE_TERMS_CANNOT_BE_EMPTY = "Both positive and negative terms cannot be empty!";
    private static final int MIN_WORD_LENGTH = 3;

    private final QueryPhase m_queryPhase;
    private final BundleIndexer m_bundleIndexer;

    public QuerySearch(final QueryPhase queryPhase, final BundleIndexer bundleIndexer) {
        m_queryPhase = queryPhase;
        m_bundleIndexer = bundleIndexer;
    }

    @Override
    public QueryResult search(final String untruest_query) {
        final var processedQuery = m_queryPhase.phase(untruest_query);

        removeSortWords(processedQuery.positiveTerms());
        removeSortWords(processedQuery.negativeTerms());

        if (processedQuery.positiveTerms().isEmpty() && processedQuery.negativeTerms().isEmpty()) {
            throw new IllegalArgumentException(BOTH_POSITIVE_AND_NEGATIVE_TERMS_CANNOT_BE_EMPTY);
        }
        if (processedQuery.positiveTerms().isEmpty()) {
            return new QueryResultFailure(ONLY_NEGATIVE_TERMS);
        }

        final Set<LinkWordCount> intersection = new HashSet<>();
        positiveQueryProcessing(processedQuery, intersection);
        negativeQueryProcessing(processedQuery, intersection);

        if (intersection.isEmpty()) {
            return new QueryResultFailure(QUERY_NOT_FOUND);
        }
        return new QueryResultSuccess(sort(intersection));
    }

    private static Set<LinkWordCount> sort(final Set<LinkWordCount> intersection) {
        // final List<LinkWordCount> linkWordCounts = new ArrayList<>(intersection);
        // linkWordCounts.sort(Comparator.comparing(LinkWordCount::count).reversed());
        // return linkWordCounts;

        // return intersection.stream()
        //         .sorted(Comparator.comparing(LinkWordCount::count).reversed())
        //         .collect(Collectors.toList());

        return intersection.stream()
                .sorted(Comparator.comparing(LinkWordCount::count).reversed())
                .collect(Collectors.toCollection(LinkedHashSet::new));

    }

    private void removeSortWords(final List<String> strings) {
        strings.removeIf(word -> word.length() < MIN_WORD_LENGTH);
    }

    private void positiveQueryProcessing(final ProcessedQuery processedQuery, final Set<LinkWordCount> intersection) {
        final var positiveWords = processedQuery.positiveTerms();
        for (String word : positiveWords) {
            if (word.length() >= MIN_WORD_LENGTH) {
                final var lm = m_bundleIndexer.words().getLinkMap();
                final List<LinkWordCount> lwc = lm.getLinkWordCount(word);
                if (intersection.isEmpty()) {
                    intersection.addAll(lwc);
                }
                intersection.retainAll(lwc);
            }
        }
    }

    // private void positiveQueryProcessing(final ProcessedQuery processedQuery, final Set<LinkWordCount> intersection) {
    //     processedQuery.positiveTerms().stream()
    //             .filter(word -> word.length() >= MIN_WORD_LENGTH)
    //             .forEach(word -> {
    //                 final var lm = m_bundleIndexer.words().getLinkMap();
    //                 final List<LinkWordCount> lwc = lm.getLinkWordCount(word);
    //                 if (intersection.isEmpty()) {
    //                     intersection.addAll(lwc);
    //                 }
    //                 else {
    //                     intersection.retainAll(lwc);
    //                 }
    //             });
    // }

    private void negativeQueryProcessing(final ProcessedQuery processedQuery, final Set<LinkWordCount> intersection) {
        final var negativeWords = processedQuery.negativeTerms();
        for (String word : negativeWords) {
            if (word.length() >= MIN_WORD_LENGTH) {
                final var lm = m_bundleIndexer.words().getLinkMap();
                final List<LinkWordCount> lwc = lm.getLinkWordCount(word);
                lwc.forEach(intersection::remove);
            }
        }
    }

    // private void negativeQueryProcessing(final ProcessedQuery processedQuery, final Set<LinkWordCount> intersection) {
    //     processedQuery.negativeTerms().stream()
    //             .filter(word -> word.length() > MIN_WORD_LENGTH)
    //             .forEach(word -> {
    //                 final var lm = m_bundleIndexer.words()
    //                         .getLinkMap();
    //                 final List<LinkWordCount> lwc = lm.getLinkWordCount(word);
    //                 lwc.forEach(intersection::remove);
    //             });
    // }

}


