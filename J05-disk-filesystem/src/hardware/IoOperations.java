package hardware;

import java.io.IOException;

public interface IoOperations {
    void read(int blockNum, byte[] buffer);

    void write(int blockNum, byte[] buffer);

    void close() throws IOException;

    int getBlockSize();

    int getNumBlocks();
}
