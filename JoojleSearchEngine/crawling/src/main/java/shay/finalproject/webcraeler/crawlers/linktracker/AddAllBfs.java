package shay.finalproject.webcraeler.crawlers.linktracker;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import shay.finalproject.webcraeler.annotations.Bfs;

import java.net.URI;
import java.util.Set;

@Bfs
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AddAllBfs extends AbstractVisitedTrackerDeque {

    @Override
    public boolean addAll(final Set<URI> links) {
        return m_urlsToVisit.addAll(links);
    }


}
