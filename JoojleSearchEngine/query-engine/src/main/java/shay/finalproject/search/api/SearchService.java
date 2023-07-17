package shay.finalproject.search.api;

import org.springframework.http.ResponseEntity;
import shay.finalproject.search.infrastructure.interfaces.QueryResult;

import javax.naming.directory.SearchResult;

public interface SearchService {
    ResponseEntity<String> search(String query);
}