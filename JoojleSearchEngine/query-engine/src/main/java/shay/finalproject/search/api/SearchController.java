package shay.finalproject.search.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shay.finalproject.search.QuerySearch;
import shay.finalproject.search.infrastructure.interfaces.QueryResult;

/**
 * @Controller is a Spring annotation that is used to mark a class as a Spring MVC controller. It is typically used to handle HTTP requests and generate HTTP responses, such as rendering HTML views. When using @Controller, you would typically return a view name or a ModelAndView object, which is resolved by a view resolver to generate the HTML response.
 * @RestController is also a Spring annotation that is used to mark a class as a Spring MVC controller, but it is specifically designed for building RESTful web services. It is a convenience annotation that combines @Controller and @ResponseBody. When using @RestController, you would typically return the object directly, and Spring will automatically convert it to the appropriate format, such as JSON or XML.
 */
@RestController
@RequestMapping("/api")
public class SearchController implements SearchService {

    private final QuerySearch querySearch;

    public SearchController(final QuerySearch querySearch) {
        this.querySearch = querySearch;
    }


    // @GetMapping("/search/{query}")
    // public String op(@PathVariable String query) {
    //     return "hi from " + query;
    //
    // }
    // @Override
    // @GetMapping("/search") // http://localhost:8080/search?q=good%20-dog
    // public String search(@RequestParam("q") String query) {
    //     final var ans = "Search results for: " + query + "\n" + querySearch.search(query).toString();
    //     final String html = ans.replaceAll("(\r\n|\n)", "<br>");
    //
    //     return html;
    // }
    @GetMapping("/search")
    @Override
    public ResponseEntity<String> search(@RequestParam("q") String query) {
        QueryResult searchResults = querySearch.search(query);

        String formattedResults = formatResults(searchResults.toString());

        return ResponseEntity.ok(formattedResults);
    }

    private String formatResults(String searchResults) {
        // Perform the necessary formatting of the search results
        // You can build a custom HTML string or use a templating engine like Thymeleaf

        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<h2>Search results:</h2>");

        searchResults = searchResults.replaceAll("(\r\n|\n)", "<br>");
        htmlBuilder.append(searchResults);

        return htmlBuilder.toString();
    }


    // @GetMapping
    // public QueryResult search(@RequestParam("q") String query) {
    //     // Parse the search query and create objects of PositiveTerm and NegativeTerm
    //     // List<SearchQuery> searchQueries = QueryParser.parse(query);
    //
    //     // Use the SearchEngine to execute the search and return the results
    //     QueryResult searchResults = querySearch.search(query);
    //
    //     return searchResults;
    // }

}
