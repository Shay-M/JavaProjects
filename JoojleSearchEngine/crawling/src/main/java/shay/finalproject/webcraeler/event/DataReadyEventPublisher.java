package shay.finalproject.webcraeler.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * The DataReadyEventPublisher class is responsible for publishing the DataReadyEvent to notify listeners when data processing is completed.
 * It uses the ApplicationEventPublisher to publish the event.
 */
@Component
public class DataReadyEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Creates a new instance of DataReadyEventPublisher with the specified ApplicationEventPublisher.
     */
    public DataReadyEventPublisher(final ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishCustomEvent(final int numUniquePages, final int numLinks, final int numIgnoredLinks) {
        final DataReadyEvent dataReadyEvent = new DataReadyEvent(this, numUniquePages, numLinks, numIgnoredLinks);
        applicationEventPublisher.publishEvent(dataReadyEvent);
    }
}
