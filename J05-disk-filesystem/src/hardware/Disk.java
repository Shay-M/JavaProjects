package hardware;
/*
hardware.Disk.java : The simulated disk uses a file to store the data (use names like data.dsk) to simulate a disk.
Each disk has  NUM_BLOCKS blocks where each block has BLOCK_SIZE bytes.
The disk class should support the following methods:
1. void read(int blockNum, byte[] buffer)—Read from the specified block number on disk into a buffer.
2. void write(int blockNum, byte[] buffer)—Write the contents of the buffer to the specified block number on disk.
*/

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/locks/ReentrantReadWriteLock.html
// https://www.baeldung.com/java-atomic-variables
public final class Disk implements IoOperations {
    // private final RandomAccessFile m_randomAccessFile;
    private final ReadWriteLock lock = new ReentrantReadWriteLock(); // like ReentrantLock
    private final Lock m_readLock = lock.readLock();
    private final Lock m_writeLock = lock.writeLock();
    private final int m_blockSize;
    private final int m_numBlocks;
    private final AtomicBoolean m_isDiskControllerClose = new AtomicBoolean(false);
    // private final AtomicInteger m_numBlocks = new AtomicInteger(0);
    private final SeekableByteChannel m_channel;

    public Disk(final Path path, final int numBlocks, final int blockSize) {
        m_blockSize = blockSize;
        m_numBlocks = numBlocks;
        try {
            m_channel = Files.newByteChannel(path,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE,
                    StandardOpenOption.READ
            );
            // final ByteBuffer buffer = ByteBuffer.allocate(blockSize * numBlocks);
            // m_channel.write(buffer);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void read(final int blockNum, final byte[] bufferToWrite) {
        verifyVariable(blockNum, bufferToWrite);
        m_readLock.lock();
        try {
            m_channel.position((long) blockNum * m_blockSize);
            m_channel.read(ByteBuffer.wrap(bufferToWrite));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            m_readLock.unlock();
        }
    }

    @Override
    public void write(final int blockNum, final byte[] bufferToRead) {
        verifyVariable(blockNum, bufferToRead);
        Objects.requireNonNull(bufferToRead, "buffer must not be null");
        if (m_isDiskControllerClose.get()) {
            throw new IllegalStateException("hardware.DiskController is closed, no new disks can be created");
        }
        m_writeLock.lock();
        try {
            m_channel.position((long) blockNum * m_blockSize);
            m_channel.write(ByteBuffer.wrap(bufferToRead));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            m_writeLock.unlock();
        }
    }

    @Override
    public void close() {
        try {
            // m_randomAccessFile.close();
            m_channel.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        m_isDiskControllerClose.set(true);
        m_writeLock.lock();
        m_readLock.lock();
    }

    private void verifyVariable(final int blockNum, final byte[] bufferToWrite) {
        Objects.requireNonNull(bufferToWrite, "buffer must not be null");
        if (blockNum < 0 || blockNum > m_numBlocks) {
            throw new IllegalArgumentException("Invalid block number: " + blockNum);
        }
        if (m_isDiskControllerClose.get()) {
            throw new IllegalStateException("hardware.DiskController is closed, no new disks can be created");
        }
        if (bufferToWrite.length != m_blockSize) {
            throw new IllegalArgumentException("Buffer is too small: " + bufferToWrite.length + ", it should have at least: " + m_blockSize);
        }
    }

    @Override
    public int getBlockSize() {
        return m_blockSize;
    }

    @Override
    public int getNumBlocks() {
        return m_numBlocks;
    }

}