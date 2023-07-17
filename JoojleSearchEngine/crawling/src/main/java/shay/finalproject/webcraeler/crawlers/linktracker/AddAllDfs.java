package shay.finalproject.webcraeler.crawlers.linktracker;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import shay.finalproject.webcraeler.annotations.Dfs;

import java.net.URI;
import java.util.Set;

@Dfs
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AddAllDfs extends AbstractVisitedTrackerDeque {

    @Override
    public boolean addAll(final Set<URI> links) {
        links.forEach(m_urlsToVisit::addFirst);
        return true;

    }


}
