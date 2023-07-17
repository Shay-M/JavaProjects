package shay.finalproject.search.infrastructure;

import shay.finalproject.datastructure.infrastructure.LinkWordCount;
import shay.finalproject.search.infrastructure.interfaces.QueryResult;

import java.util.Set;

public record QueryResultSuccess(Set<LinkWordCount> linkWordCounts) implements QueryResult {

    @Override
    public String toString() {
        final StringBuilder mas = new StringBuilder("Query executed:" + System.lineSeparator());
        for (LinkWordCount m : linkWordCounts) {
            mas.append(m.toString()).append(System.lineSeparator());

        }
        return mas.toString();
    }
}



