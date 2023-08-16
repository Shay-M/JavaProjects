package shay.space.station.ui.console;

import org.springframework.stereotype.Component;
import shay.space.station.ui.infra.InputSupplier;

import java.util.Scanner;

@Component
public class UserInput implements InputSupplier {
    private final Scanner m_scanner = new Scanner(System.in);

    @Override
    public String getString() {
        return m_scanner.nextLine();
    }

    @Override
    public int getNumber() {
        return m_scanner.nextInt();
    }
}
