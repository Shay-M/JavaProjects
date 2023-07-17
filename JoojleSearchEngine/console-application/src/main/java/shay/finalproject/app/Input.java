package shay.finalproject.app;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import shay.finalproject.search.infrastructure.interfaces.SupplierQuery;

import java.util.Scanner;

@Component
@Primary
public class Input implements SupplierQuery {

    private final Scanner m_scanner = new Scanner(System.in);

    @Override
    public String getQuery() {
        String query = "";
        do {
            System.out.println("\nEnter a query to search:");
            query = m_scanner.nextLine();
        } while (query.isEmpty());
        return query;
    }

}
