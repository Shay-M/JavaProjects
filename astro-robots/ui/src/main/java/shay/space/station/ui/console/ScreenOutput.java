package shay.space.station.ui.console;

import org.springframework.stereotype.Component;

@Component
public class ScreenOutput implements Output {
    @Override
    public void print(final String message) {
        System.out.println(message);
    }
}
