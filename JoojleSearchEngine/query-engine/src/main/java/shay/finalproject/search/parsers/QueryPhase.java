package shay.finalproject.search.parsers;

import org.springframework.stereotype.Component;
import shay.finalproject.search.parsers.infrastructure.Parser;
import shay.finalproject.search.parsers.infrastructure.ProcessedQuery;

import java.util.*;

@Component
public class QueryPhase implements Parser {
    private static final String SPACE = "\\s+";
    private static final String NEGATIVE = "-";

    public ProcessedQuery phase(final String untruest_query) {
        if (validateQuery(untruest_query)) {
            return new ProcessedQuery(Collections.emptyList(), Collections.emptyList());
        }
        final String query = untruest_query.toLowerCase();
        final var words = query.split(SPACE);
        assert words.length != 0;

        final List<String> positiveTerms = new ArrayList<>();
        final List<String> negativeTerms = new ArrayList<>();

        for (String term : words) {
            if (term.startsWith(NEGATIVE)) {
                negativeTerms.add(term.substring(1).toLowerCase());
            }
            else {
                positiveTerms.add(term.toLowerCase());
            }
        }

        return new ProcessedQuery(positiveTerms, negativeTerms);
    }

    private boolean validateQuery(final String untruest_query) {
        // Objects.requireNonNull(untruest_query);
        return untruest_query == null || untruest_query.length() == 0;
    }

}
