package shay.finalproject.webcraeler.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * The DataReadyEvent class represents an event that indicates the readiness of data in the web crawler.
 * It is fired when the crawling process is complete and contains information about the crawled data.
 */
@Getter
public class DataReadyEvent extends ApplicationEvent {
    final int numUniquePages;
    final int numLinks;
    final int numIgnoredLinks;

    /**
     * Constructs a new DataReadyEvent with the specified parameters.
     *
     * @param source          The object on which the event initially occurred.
     * @param numUniquePages  The number of unique pages crawled.
     * @param numLinks        The total number of links encountered during crawling.
     * @param numIgnoredLinks The number of ignored links during crawling.
     */
    public DataReadyEvent(final Object source, final int numUniquePages, final int numLinks, final int numIgnoredLinks) {
        super(source);
        this.numUniquePages = numUniquePages;
        this.numLinks = numLinks;
        this.numIgnoredLinks = numIgnoredLinks;
    }

}
