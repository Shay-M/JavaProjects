package simplefilesystem;

import hardware.DiskController;
import hardware.IoOperations;
import org.junit.jupiter.api.*;
import simplefilesystem.inodes.Inode;

import java.nio.file.Path;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static simplefilesystem.SysParameters.*;


class FileSystemTest {
    private static final String PATH = "save/disks/";
    private static final String DISK_FILE_TYPE = ".dsk";
    private static final String NAME_TXT = "name.txt";
    private static DiskController diskController;
    private static IoOperations ioOperations;
    private static FileSystem fileSystem;


    @BeforeEach
    void setUp() {
        diskController = DiskController.getInstance(Path.of(PATH), DISK_FILE_TYPE, NUM_BLOCKS, BLOCK_SIZE);
        ioOperations = diskController.get(1);
        fileSystem = new FileSystem(ioOperations);
        fileSystem.createFile(NAME_TXT);
    }


    @AfterAll
    static void format() {
       fileSystem.format(ioOperations);
        diskController.shutdown();
    }


    @Test
    void stringTest() {
        var file = fileSystem.open(NAME_TXT);

        for (int i = 0; i < 10; i++) {
            file.write("" + i + 100);
        }

        file.setPosition(0);
        for (int i = 0; i < 10; i++) {
            var d = file.readString();
            assertEquals("" + i + 100, d);
        }

        fileSystem.deleteFile(NAME_TXT);
        assertThrowsExactly(NoSuchElementException.class,
                () -> fileSystem.open(NAME_TXT)
        );


    }

    @Test
    void integerTest() {
        var file = fileSystem.open(NAME_TXT);

        for (int i = 0; i < 10; i++) {
            file.write(i + 100);
        }

        file.setPosition(0);
        for (int i = 0; i < 10; i++) {
            var d = file.readInteger();
            assertEquals(i + 100, d);
        }

        fileSystem.deleteFile(NAME_TXT);
        assertThrowsExactly(NoSuchElementException.class,
                () -> fileSystem.open(NAME_TXT)
        );
    }

}