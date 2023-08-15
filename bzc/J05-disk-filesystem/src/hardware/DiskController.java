package hardware;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DiskController {
    private static final String DISK_FILE_PREFIX = "disk-";
    private static final String TEXT_NAME_FORMAT_NUMBER = "%02d";
    private static final AtomicReference<DiskController> instance = new AtomicReference<DiskController>();

    private final ConcurrentHashMap<Integer, IoOperations> disks = new ConcurrentHashMap<>();
    private final Path m_path;
    private final String m_diskFileType;
    private final int m_blockSize;
    private final int m_numBlocks;
    private final AtomicBoolean closed = new AtomicBoolean();

    final Lock m_lock = new ReentrantLock();

    private DiskController(final Path path, final String diskFileType, final int numBlocks, final int blockSize) {
        m_path = path;
        m_diskFileType = diskFileType;
        m_blockSize = blockSize;
        m_numBlocks = numBlocks;
    }

    public static DiskController getInstance(final Path path, final String diskFileType, final int numBlocks, final int blockSize) {
        if (instance.get() == null) {
            synchronized (DiskController.class) {
                if (instance.get() == null) {
                    instance.set(new DiskController(path, diskFileType, numBlocks, blockSize));
                }
            }
        }

        return instance.get();
    }

    public IoOperations get(final int diskNumber) {
        if (closed.get()) {
            throw new IllegalStateException("DiskController is closed, no new disks can be created");
        }
        final String fileName = DISK_FILE_PREFIX + String.format(TEXT_NAME_FORMAT_NUMBER, diskNumber) + m_diskFileType;
        final Path filePath = Paths.get(m_path.toString(), fileName);
        try {
            if (!Files.exists(filePath)) {
                Files.createDirectories(m_path);
                Files.createFile(filePath);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        disks.putIfAbsent(diskNumber, new Disk(filePath, m_numBlocks, m_blockSize));
        return disks.get(diskNumber);
    }


    public void shutdown() {
        m_lock.lock();
        closed.set(true);

        for (IoOperations ioOperations : disks.values()) {
            try {
                ioOperations.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        disks.clear();

    }

    public final int count() {
        return disks.size();
    }
}

