package shay.space.station.ui.console;

import org.springframework.stereotype.Component;
import shay.space.station.ui.infra.Output;

@Component
public class ScreenOutput implements Output {
    @Override
    public void print(final String message) {
        System.out.println(message);
    }
}
