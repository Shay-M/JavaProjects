package shay.finalproject.datastructure.infrastructure;

import java.net.URI;
import java.util.Objects;

public record LinkWordCount(URI url, Double count) {
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        LinkWordCount that = (LinkWordCount) obj;
        return Objects.equals(url, that.url);
    }

    @Override
    public String toString() {
        String mas = System.lineSeparator();
        // mas += "• Page title: " + "%page_tile%\n";
        mas += "• Page's Link: " + url + System.lineSeparator();
        mas += "• Number of times the search word appears in the page: " + count + System.lineSeparator();
        return mas;
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}


