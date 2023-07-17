// package shay.finalproject.controller;
//
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
// import shay.finalproject.search.infrastructure.interfaces.QueryResult;
// import shay.finalproject.search.infrastructure.interfaces.SearchEngine;
//
// @RestController
// @RequestMapping("/api/search")
// public class Control2 {
//
//     @Autowired
//     private SearchEngine searchEngine;
//
//     @GetMapping("/{query}")
//     public QueryResult search(@PathVariable String query) {
//         // List<SearchResult> results = searchEngine.search(query);
//         var results = searchEngine.search(query);
//         System.out.println(query);
//         System.out.println(results);
//         return results;
//     }
// }
