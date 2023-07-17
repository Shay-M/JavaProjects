package shay.finalproject.webcraeler.crawlers.linktracker;

import org.springframework.stereotype.Component;
import shay.finalproject.webcraeler.Indexer;

import java.net.URI;

@Component
public record BundleIndexer(Indexer<String> words, Indexer<URI> links) {
}
