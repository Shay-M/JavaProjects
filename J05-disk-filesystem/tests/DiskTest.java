import hardware.Disk;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

class DiskTest {
    public static final int BLOCK_SIZE = 10;
    public static final int NUM_BLOCKS = 5;
    private static final String TEST_FILE = "testFile.txt";
    public static final String FOLDER_DISKS = "disks";
    private static Path testFolder;
    private static Path testFile;

    @BeforeAll
    public static void setup() throws IOException {
        testFolder = Path.of(FOLDER_DISKS);
        Files.createDirectory(testFolder);
        testFile = Path.of(FOLDER_DISKS, TEST_FILE);
    }

    @AfterAll
    public static void cleanupFolder() throws IOException {
        Files.delete(testFolder);
    }

    // @BeforeEach
    // public void init() {
    //     diskFite = testFolder.resolve(TEST_FILE);
    // }

    @AfterEach
    public void removeFile() throws IOException {
        Files.delete(testFile);
    }

    @Test
    void testReadWrite() {
        final Disk disk = new Disk(testFile, NUM_BLOCKS, BLOCK_SIZE);

        final byte[] buffer = new byte[BLOCK_SIZE];
        for (int i = 0; i < BLOCK_SIZE; ++i) {
            buffer[i] = (byte) i;
        }
        disk.write(0, buffer);

        final byte[] readBuffer = new byte[BLOCK_SIZE];
        disk.read(0, readBuffer);

        // disk.close();
        Assertions.assertArrayEquals(buffer, readBuffer);
    }

    // @Test
    // void testReadFromB1() {
    //     final int blockSize = 10;
    //     final hardware.Disk disk = new hardware.Disk(testFile, NUM_BLOCKS, BLOCK_SIZE);
    //
    //     final byte[] buffer = new byte[blockSize * 2];
    //     for (int i = 0; i < blockSize * 2; i++) {
    //         buffer[i] = (byte) i;
    //     }
    //     disk.write(0, buffer);
    //
    //     final byte[] readBuffer = new byte[blockSize];
    //     disk.read(1, readBuffer);
    //
    //     Assertions.assertArrayEquals(buffer, readBuffer);
    // }

    @Test
    public void testReadWithNegativeBlockNum() {
        final Disk disk = new Disk(testFile, NUM_BLOCKS, BLOCK_SIZE);

        final byte[] buffer = new byte[BLOCK_SIZE];
        final int blockNum = -1;
        final IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> disk.read(blockNum, buffer));

        Assertions.assertEquals("Invalid block number: " + blockNum, ex.getMessage());
    }

    @Test
    public void testReadWithOutboundBlockNum() {
        final Disk disk = new Disk(testFile, NUM_BLOCKS, BLOCK_SIZE);

        final byte[] buffer = new byte[BLOCK_SIZE];
        final int blockNum = NUM_BLOCKS + 1;
        final IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> disk.read(blockNum, buffer));
        // IndexOutOfBoundsException

        Assertions.assertEquals("Invalid block number: " + blockNum, ex.getMessage());

        // assertThrowsExactly(IllegalArgumentException.class,
        //         () -> disk.read(blockNum, buffer)
        // );
    }

    @Test
    public void testReadWithTooSmallBuffer() {
        final Disk disk = new Disk(testFile, NUM_BLOCKS, BLOCK_SIZE);

        final byte[] buffer = new byte[BLOCK_SIZE - 1];
        final int blockNum = 0;
        final IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> disk.read(blockNum, buffer));
        Assertions.assertEquals("Buffer is too small, it should have at least" + BLOCK_SIZE, ex.getMessage());
    }

    @Test
    public void testClose() {
        final Disk disk = new Disk(testFile, NUM_BLOCKS, BLOCK_SIZE);

        disk.close();
        final byte[] buffer = new byte[BLOCK_SIZE];
        final IllegalStateException ex = Assertions.assertThrows(IllegalStateException.class,
                () -> disk.read(0, buffer));
        Assertions.assertEquals("hardware.DiskController is closed, no new disks can be created", ex.getMessage());
    }

    @Test
    public void testWriteWithNullBuffer() {
        final Disk disk = new Disk(testFile, NUM_BLOCKS, BLOCK_SIZE);

        final byte[] buffer = null;
        final int blockNum = 0;
        final NullPointerException ex = Assertions.assertThrows(NullPointerException.class,
                () -> disk.write(blockNum, buffer));
        Assertions.assertEquals("buffer must not be null", ex.getMessage());
    }

    @Test
    public void testReadWriteWithLargeFileSize() {
        final Disk disk = new Disk(testFile, NUM_BLOCKS, BLOCK_SIZE);

        final byte[] buffer = new byte[BLOCK_SIZE];
        final byte[] readBuffer = new byte[BLOCK_SIZE];
        final Random rand = new Random();
        rand.nextBytes(buffer);
        disk.write(0, buffer);
        disk.read(0, readBuffer);

        Assertions.assertArrayEquals(buffer, readBuffer);
    }

}

