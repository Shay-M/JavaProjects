package shay.finalproject.search.parsers.infrastructure;

import java.util.List;
import java.util.Set;

public record ProcessedQuery(List<String> positiveTerms, List<String> negativeTerms) {
}
