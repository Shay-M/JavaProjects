package gameoflife.filessave;

import java.io.IOException;

public class CreateFolderException extends RuntimeException {
    public CreateFolderException(final IOException ex) {
        super(ex);
    }
}
