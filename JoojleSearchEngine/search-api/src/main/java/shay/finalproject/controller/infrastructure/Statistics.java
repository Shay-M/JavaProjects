package shay.finalproject.controller.infrastructure;

public record Statistics(boolean crawlingInProgress, int pagesVisited, int linksDiscovered, int maxDepthReached,
                         long crawlDuration) {
}
