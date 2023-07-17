package shay.finalproject.webcraeler.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
// @ConfigurationProperties(prefix = "crawler")
@PropertySource("classpath:crawler.properties")
@Data
public class CrawlerConfig {
    @Value("${crawler.maxDepth:-1}") // Default value is -1 (unlimited depth)
    private int maxDepth;
    @Value("${crawler.maxPages:-1}") //  Default value is -1 (unlimited pages)
    private int maxPages;
    @Value("${crawler.startUrl:https://www.w3schools.com/}")
    private String startUrl;
    @Value("${spring.profiles.active:BFS}")
    private String crawlMode;
    @Value("${crawler.numberOfThread:2}")
    private int numberOfThread;
}


// @Configuration
// @ComponentScan("shay.finalproject.crawlers.bfs")
// @PropertySource("classpath:crawler.properties")

