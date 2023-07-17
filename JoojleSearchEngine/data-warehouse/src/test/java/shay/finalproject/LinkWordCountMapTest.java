package shay.finalproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shay.finalproject.datastructure.LinkMap;
import shay.finalproject.datastructure.infrastructure.LinkWordCount;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LinkWordCountMapTest {

    LinkMap linkMap;

    @BeforeEach
    void setUp() {
        linkMap = new LinkMap();
    }

    @Test
    void testAddLinkForExistingWord() throws URISyntaxException {
        LinkWordCount linkWordCount1 = new LinkWordCount(new URI("http://example1.com"), 1.0);
        LinkWordCount linkWordCount2 = new LinkWordCount(new URI("http://example2.com"), 2.0);
        linkMap.add("example", linkWordCount1);
        linkMap.add("example", linkWordCount2);
        assertTrue(linkMap.contains("example"));
        assertFalse(linkMap.contains("dog"));

        List<LinkWordCount> linkWordCounts = linkMap.getLinkWordCount("example");
        assertEquals(2, linkWordCounts.size());
        assertEquals(1, linkMap.getMap().size());
        assertTrue(linkWordCounts.contains(linkWordCount1));
        assertTrue(linkWordCounts.contains(linkWordCount2));

    }

    @Test
    void testGetLinksForNonexistentWord() {
        List<LinkWordCount> linkWordCounts = linkMap.getLinkWordCount("example");
        assertTrue(linkWordCounts.isEmpty());
    }

    @Test
    void testGetLinksForExistingWord() throws URISyntaxException {
        LinkWordCount linkWordCount1 = new LinkWordCount(new URI("http://example1.com"), 1.0);
        LinkWordCount linkWordCount2 = new LinkWordCount(new URI("http://example2.com"), 2.0);
        linkMap.add("example", linkWordCount1);
        linkMap.add("example", linkWordCount2);
        List<LinkWordCount> linkWordCounts = linkMap.getLinkWordCount("example");
        assertEquals(2, linkWordCounts.size());
        assertTrue(linkWordCounts.contains(linkWordCount1));
        assertTrue(linkWordCounts.contains(linkWordCount2));
    }

    @Test
    void testAddingSameLinkTwice() throws URISyntaxException {
        LinkMap linkMap = new LinkMap();
        LinkWordCount linkWordCount1 = new LinkWordCount(new URI("https://www.example.com"), 2.0);
        LinkWordCount linkWordCount2 = new LinkWordCount(new URI("https://www.example.com"), 2.0);
        String word = "example";

        linkMap.add(word, linkWordCount1);
        linkMap.add(word, linkWordCount2);

        List<LinkWordCount> linkWordCounts = linkMap.getLinkWordCount(word);
        assertEquals(1, linkWordCounts.size());
        assertEquals(1, linkMap.getMap().size());
        assertEquals(linkWordCount1, linkWordCounts.get(0));
    }


}