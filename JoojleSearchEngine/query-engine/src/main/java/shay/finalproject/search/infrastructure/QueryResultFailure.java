package shay.finalproject.search.infrastructure;

import shay.finalproject.search.infrastructure.interfaces.QueryResult;

public record QueryResultFailure(String message) implements QueryResult {

    @Override
    public String toString() {
        return "Message: " + message;
    }
}

