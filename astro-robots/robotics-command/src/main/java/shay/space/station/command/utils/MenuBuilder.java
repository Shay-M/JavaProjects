package shay.space.station.command.utils;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
@Component
public class MenuBuilder {

    public StringBuilder Build(final Collection<?> collection) {
        final StringBuilder sb = new StringBuilder();
        final AtomicInteger index = new AtomicInteger();
        for (var c : collection) {
            sb.append(index.incrementAndGet());
            sb.append(") ");
            sb.append(c.toString());
            sb.append("\n");
        }
        return sb;

    }
}
