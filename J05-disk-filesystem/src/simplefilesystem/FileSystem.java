package simplefilesystem;

import hardware.IoOperations;
import simplefilesystem.exception.EmptyFileNameException;
import simplefilesystem.inodes.Inode;
import simplefilesystem.inodes.InodeSystem;
import simplefilesystem.sb.SuperBlock;
import simplefilesystem.sb.SuperBlockSystem;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static simplefilesystem.SysParameters.*;

public final class FileSystem {
    private final int m_blockSize;
    private final int m_totalInodes;
    private final InodeSystem m_inodeSystem;
    private final SuperBlockSystem m_superBlockSystem;
    private final IoOperations m_disk;
    private final BitSet m_blocksNumbers;


    public FileSystem(final IoOperations disk) {
        Objects.requireNonNull(disk, "Disk must not be null");
        m_disk = disk;
        m_blockSize = disk.getBlockSize();
        m_superBlockSystem = new SuperBlockSystem(disk);
        m_inodeSystem = new InodeSystem(disk);

        final byte[] bufferSuperBlock = new byte[m_blockSize];
        disk.read(0, bufferSuperBlock);

        SuperBlock superBlock = new SuperBlock(bufferSuperBlock);
        m_blocksNumbers = new BitSet(disk.getNumBlocks());

        if (!superBlock.isValid()) {
            superBlock = format(disk);
        }
        superBlock = format(disk); //<< remove me
        m_totalInodes = superBlock.getInodes();
    }

    public SuperBlock format(final IoOperations disk) {
        final var superBlock = m_superBlockSystem.createSuperBlock();
        m_superBlockSystem.writeSuperBlock(superBlock);
        m_blocksNumbers.set(0);

        // dot file
        var dataSize = writeDotFile(disk);
        // inode
        final var inode = new Inode(dataSize, 2);

        m_inodeSystem.writeInode(disk, inode, INODE_BLOCK, 0);
        m_blocksNumbers.set(1);

        return superBlock;
    }

    private int writeDotFile(final IoOperations disk) {
        writeAFileToTheDotFile(ROOT_NAME_FILE, INDEX_ROOT_FILE);

        final var dataString = (ROOT_NAME_FILE + SEPARATOR + INDEX_ROOT_FILE + SEPARATOR).getBytes(StandardCharsets.UTF_8);
        // Arrays.fill(data, (byte) 0);

        // copy to data
        byte[] data = new byte[m_blockSize];
        System.arraycopy(dataString,
                0, data, 0, dataString.length);

        disk.write(DOT_BLOCK, data);
        return dataString.length;

    }

    byte[] readBlock(final int numBlocks) {
        final byte[] inodesBlock = new byte[m_blockSize];
        m_disk.read(numBlocks, inodesBlock);
        return inodesBlock;
    }

    // todo
    public void read(final Inode inode, final int position, ByteBuffer buffer) {
        final List<Integer> directsFromInode = inode.getDirectAsList();

        buffer.put(readBlock(3));

    }

    public void write(final Inode inode, final int position, final ByteBuffer bufferNewDate, final int inodeNumber) {
        final List<byte[]> blocksBuffers = bufferSplitsToBlocks(bufferNewDate, position);
        final List<Integer> directsFromInode = inode.getDirectAsList();
        var offset = position % m_blockSize;

        for (byte[] blocksBuffer : blocksBuffers) {
            final int blockNumber = getBlockNumber(position, directsFromInode, inode); // inode
            if (!directsFromInode.contains(blockNumber)) {
                directsFromInode.add(blockNumber);
            }
            final ByteBuffer byteBuffer = copyOldData(blockNumber);
            byteBuffer.put(offset, blocksBuffer, 0, blocksBuffer.length);
            m_disk.write(blockNumber, byteBuffer.array());
            offset = 0;
        }

        // inode ,
        inode.setSize(offset + bufferNewDate.array().length); // todo
        inode.setDirects(directsFromInode); // todo it only 5
        m_inodeSystem.writeInode(m_disk, inode, INODE_BLOCK, inodeNumber);

    }

    private ByteBuffer copyOldData(final int blockNumber) {
        final var byteBuffer = ByteBuffer.allocate(m_blockSize);
        final var oldData = readBlock(blockNumber);
        byteBuffer.put(oldData, 0, oldData.length);
        return byteBuffer;
    }

    private List<byte[]> bufferSplitsToBlocks(final ByteBuffer buffer, int position) {
        final List<byte[]> blocks = new ArrayList<>();
        final var blockCount = (int) Math.ceil((double) buffer.limit() / m_blockSize);
        final var bufferInByte = buffer.array();
        var cp = 0;
        for (int i = 0; i < blockCount; ++i) {
            final int from = cp;// position + i * m_blockSize;
            int length = m_blockSize - position % m_blockSize;
            if (from + length > buffer.limit()) {
                length = buffer.limit() - from;
            }
            final var slice = new byte[length];
            System.arraycopy(bufferInByte, from, slice, 0, length);
            blocks.add(slice);
            cp += length;
            position = 0;
        }
        return blocks;

        // final int blockCount = (int) Math.ceil((double) (buffer.capacity() - position) / m_blockSize);
        // final List<byte[]> blocks = new ArrayList<>();

        // for (int i = 0; i < blockCount; ++i) {
        //     final int start = i * m_blockSize;
        //     final int end = Math.min(start + m_blockSize, buffer.capacity());
        //     // final var slice = buffer.slice(start, end).array(); // not good in big data
        //     // blocks.add(slice);
        //
        //     buffer.position(start + position);
        //     buffer.limit(end);
        //     final byte[] blockData = new byte[buffer.remaining()];
        //     buffer.get(start + position,blockData);
        //     blocks.add(blockData);
        //
        //     position = 0;
        // }
        // return blocks;
    }

    private int getBlockNumber(final int position, final List<Integer> directsFromInode, final Inode inode) {
        final var blockNumberIndex = position / m_blockSize; // which block in array
        if (blockNumberIndex < directsFromInode.size()) {
            if (directsFromInode.get(blockNumberIndex) == 0) {
                return getEmptyBlockNumber();
            }
            return directsFromInode.get(blockNumberIndex);
        }
        else {
            var indirectNum = inode.getIndirectNum();
            if (indirectNum == 0) {
                return createIndirectNumbersBlock(); // todo

            }
            else {
                // getIndirect(indirectNum, blockNumberIndex);
                return 0; // todo
            }


        }

    }

    private int getIndirect(final int indirectNum, final int blockNumberIndex) {
        assert indirectNum != 0;
        final List<Integer> blocksNumbers = new ArrayList<>();
        var blockBuffer = readBlock(indirectNum);
        var byteBuffer = ByteBuffer.wrap(blockBuffer).asIntBuffer().array();

        // byteBuffer[blockNumberIndex]

        return 0;

    }

    private int createIndirectNumbersBlock() {
        // inode.setIndirect(getEmptyBlockNumber());
        return getEmptyBlockNumber();
    }

    int getEmptyBlockNumber() {
        var blockNumber = m_blocksNumbers.nextClearBit(DOT_BLOCK + 1);
        // if (blockNumber == -1) {
        //     throw new
        // }
        return blockNumber;
    }


    public void writeAFileToTheDotFile(final String fileName, final int inodeNumber) {
        // copy old data to oldDataString
        final byte[] currentInodesBlock = new byte[m_blockSize];
        m_disk.read(SysParameters.DOT_BLOCK, currentInodesBlock);
        final String oldDataString = new String(ByteBuffer.wrap(currentInodesBlock).array(),
                StandardCharsets.UTF_8).trim();

        //
        final byte[] newInodesBlock = new byte[m_blockSize];
        final var oldDataBytes = oldDataString.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(
                oldDataBytes,
                0,
                newInodesBlock,
                0,
                oldDataString.length()
        );

        final String newDataString = fileName + SEPARATOR + inodeNumber + SEPARATOR;
        final var newDataBytes = newDataString.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(
                newDataBytes,
                0,
                newInodesBlock,
                oldDataString.length(),
                newDataString.length()
        );

        m_disk.write(DOT_BLOCK, newInodesBlock);
    }

    private void validateFileInodeNumber(final int inodeNumber) {
        Objects.requireNonNull(inodeNumber, "inodeNumber must not be null");
        if (inodeNumber < 0) {
            throw new IllegalArgumentException("inodeNumber cannot be under zero");
        }

    }

    private void validateFileName(final String fileName) {
        Objects.requireNonNull(fileName, "fileName must not be null");

        if (fileName.trim().equals("")) {
            throw new EmptyFileNameException("fileName must not be empty name");
        }

        if (fileName.matches(".*" + INVALID_CHARS + ".*")) {
            throw new EmptyFileNameException("The string contains invalid characters.");
        }

    }

    private int findsInodeIdAvailable() {
        final var readBlock = readBlock(DOT_BLOCK);
        final var filesNamesInodes = new String(readBlock, StandardCharsets.UTF_8).trim().split(SEPARATOR);
        final var fileNames = new ArrayList<Integer>();
        for (int i = 0; i < filesNamesInodes.length - 1; i += 2) {
            fileNames.add(Integer.parseInt(filesNamesInodes[i + 1])); // todo see
        }
        int i = 0;
        for (; i < m_totalInodes; ++i) {
            if (!fileNames.contains(i)) {
                assert i != 0;
                return i;
            }
        }
        throw new RuntimeException("No Inode Available");
    }

    public List<String> dir() { // final IoOperations disk
        final byte[] readBlock = readBlock(DOT_BLOCK);
        final String[] filesNamesInodes = new String(readBlock, StandardCharsets.UTF_8).trim().split(SEPARATOR);
        final List<String> fileNames = new ArrayList<>();

        // for (int i = 0; i < readBlock.length; i += FILE_NAME_SIZE) {
        //     byte[] subarray = Arrays.copyOfRange(readBlock, i, i + FILE_NAME_SIZE);
        //     fileNames.add(new String(subarray));
        // }

        for (int i = 0; i < filesNamesInodes.length; i += 2) {
            fileNames.add(filesNamesInodes[i]);
        }
        return fileNames;
    }

    public boolean createFile(final String fileName) {
        validateFileName(fileName);
        final var fileNames = dir();
        if (fileNames.contains(fileName)) {
            return false;
        }
        final var inodeNumberAvailable = findsInodeIdAvailable();
        writeAFileToTheDotFile(fileName, inodeNumberAvailable);

        final var inode = new Inode();
        m_inodeSystem.writeInode(m_disk, inode, INODE_BLOCK, inodeNumberAvailable);
        return true;
    }

    public File open(final String fileName) {
        validateFileName(fileName);
        final var fileNames = dir();
        if (!fileNames.contains(fileName)) {
            throw new NoSuchElementException("File : " + fileName + " not found!");
        }

        final byte[] dotBlock = readBlock(DOT_BLOCK);
        final String dotBlockString = new String(dotBlock, StandardCharsets.UTF_8).trim();
        final String[] files = dotBlockString.split(SEPARATOR);

        final int inodeNumber = searchByNameAndGetInodeNumber(files, fileName);

        var inode = m_inodeSystem.readInode(inodeNumber);
        assert 1 == inode.getValid();

        return new File(fileName, this, inode, inodeNumber);
    }

    public void deleteFile(final String fileName) {
        validateFileName(fileName);
        // todo
        final byte[] dotBlock = readBlock(DOT_BLOCK);
        final String dotBlockString = new String(dotBlock, StandardCharsets.UTF_8).trim();
        final String[] files = dotBlockString.split(SEPARATOR);
        final int inodeNumber = searchByNameAndGetInodeNumber(files, fileName);
        m_inodeSystem.deleteInode(inodeNumber); // todo

        // Remove file name and inode number from dot block
        final String newDotBlockString = dotBlockString.replace(fileName + SEPARATOR + inodeNumber + SEPARATOR, "");
        final byte[] newDotBlock = newDotBlockString.getBytes(StandardCharsets.UTF_8);
        final byte[] newDotBlockWithSize = Arrays.copyOf(newDotBlock, m_blockSize); // XX

        m_disk.write(DOT_BLOCK, newDotBlockWithSize);


    }


    private int searchByNameAndGetInodeNumber(final String[] files, final String fileName) {
        final int inodeNumber;
        for (int i = 0; i < files.length - 1; i += 2) {
            if (files[i].equals(fileName)) {
                inodeNumber = Integer.parseInt(files[i + 1]);
                return inodeNumber;
            }
        }
        throw new NoSuchElementException("no file name: " + fileName + " found ");
    }


}
