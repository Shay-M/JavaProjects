import hardware.DiskController;
import hardware.IoOperations;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class DiskControllerTest {
    private static final Path TEST_FOLDER = Path.of("./disks/");
    private static final String DISK_FILE_TYPE = ".txt";
    private static final String TEST_FILE = "disk-00.txt";
    private static final int BLOCK_SIZE = 10;
    private static final int NUM_BLOCKS = 5;
    private static final int DISK_NUMBER = 0;

    @AfterAll
    public static void cleanupFolder() throws IOException {
        Files.delete(TEST_FOLDER);
    }

    @AfterEach
    public void removeFile() throws IOException {
        Files.delete(Paths.get(TEST_FOLDER.toString(), TEST_FILE));
    }

    @Test
    public void testGetDisk() {
        final DiskController diskController = DiskController.getInstance(TEST_FOLDER, DISK_FILE_TYPE, NUM_BLOCKS, BLOCK_SIZE);
        final IoOperations ioOperations = diskController.get(DISK_NUMBER);

        Assertions.assertNotNull(ioOperations);
        diskController.shutdown();
    }

    // @Test
    // public void testShutdown() {
    //     final hardware.DiskController diskController = hardware.DiskController.getInstance(TEST_FOLDER, DISK_FILE_TYPE, NUM_BLOCKS, BLOCK_SIZE);
    //     diskController.shutdown();
    //     final IllegalStateException ex = Assertions.assertThrows(IllegalStateException.class,
    //             () -> diskController.get(DISK_NUMBER));
    //     // Assertions.assertEquals("hardware.DiskController is closed, no new disks can be created", ex.getMessage());
    // }

}