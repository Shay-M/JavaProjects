package shay.finalproject.extractor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shay.finalproject.webcraeler.extractor.TextExtractorJsoup;
import shay.finalproject.webcraeler.extractor.WebTextProcessor;

import java.util.List;


class TextExtractorJsoupTest {
    private final TextExtractorJsoup textExtractor = new TextExtractorJsoup();
    private final WebTextProcessor cleanTextToString = new WebTextProcessor();

    @BeforeEach
    void setUp() {

    }

    @Test
    public void testExtractTextToBigString() {
        String html = "<html><body><p>This is a sample text. </p></body></html>";
        Document doc = Jsoup.parse(html);
        String result = textExtractor.extractTextToBigString(doc);
        Assertions.assertEquals("This is a sample text.", result);
    }

    @Test
    public void testCleanTextToString() {
        String input = "This is a   Sample Text. With random punctuation! It's \"quoted\", of course.";
        String result =cleanTextToString.cleanText(input);
        Assertions.assertEquals("this is a sample text with random punctuation it's quoted of course", result);

    }

    @Test
    public void testBigStringToList() {
        String input = "this is a sample text with random punctuation its quoted of course";

        List<String> result = cleanTextToString.textToList(input);
        Assertions.assertEquals(List.of("this", "is", "a", "sample", "text", "with", "random", "punctuation", "its", "quoted", "of", "course"), result);
    }

}