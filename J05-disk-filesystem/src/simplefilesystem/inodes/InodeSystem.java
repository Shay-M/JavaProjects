package simplefilesystem.inodes;

import hardware.IoOperations;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

import static simplefilesystem.SysParameters.INODE_BLOCK;

public class InodeSystem {
    private static final int INT_SIZE = 4;
    private static final int INODES_SIZE = 32;
    private final IoOperations m_disk;
    private final int m_blockSize;
    // private final int m_totalInodes;

    public InodeSystem(final IoOperations disk) {
        m_disk = disk;
        m_blockSize = disk.getBlockSize();
        // m_totalInodes = superBlock.getInodes();
    }

    public void writeInode(final IoOperations disk, final Inode inode, final int numBlocks, final int inodeNumberAvailable) {
        // var byteBuffer1 = ByteBuffer.allocate(m_blockSize);
        // byteBuffer1.putInt(inode.getValid());
        // byteBuffer1.putInt(inode.getSize());
        // byteBuffer1.put(0,)
        //
        // byteBuffer1.array();

        final byte[] inodesBlock = new byte[m_blockSize];
        m_disk.read(numBlocks, inodesBlock);
        // assert INODES_SIZE == inodesBlock.length;
        final var location = inodeNumberAvailable * INODES_SIZE;
        final var bufferValid = ByteBuffer.allocate(INT_SIZE).putInt(inode.getValid()).array();
        final var bufferSize = ByteBuffer.allocate(INT_SIZE).putInt(inode.getSize()).array();
        System.arraycopy(bufferValid, 0, inodesBlock, location, bufferValid.length);
        System.arraycopy(bufferSize, 0, inodesBlock, location + bufferValid.length, bufferSize.length);

        final var directFromInode = inode.getDirect();
        final ByteBuffer byteBuffer = ByteBuffer.allocate(directFromInode.length * INT_SIZE);
        final IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(directFromInode);
        byte[] byteArray = byteBuffer.array();
        System.arraycopy(byteArray, 0, inodesBlock, bufferSize.length, directFromInode.length);

        m_disk.write(numBlocks, inodesBlock);
    }

    public void deleteInode(final int inodeNumberToResat) {
        final Inode inode = new Inode();
        writeInode(m_disk, inode, INODE_BLOCK, inodeNumberToResat);
    }

    // public Inode getInode(final int inodeNumber) {
    //     final byte[] inodesBlock = new byte[m_blockSize];
    //     m_disk.read(INODE_BLOCK, inodesBlock);
    //     var location = inodeNumber * INODES_SIZE;
    //     inodesBlock[location]
    //     final Inode inode = new Inode();
    //     writeInode(m_disk, inode, INODE_BLOCK, inodeNumberToResat);
    // }

    public Inode readInode(final int inodeNumber) {
        final byte[] inodesBlock = new byte[m_blockSize];
        m_disk.read(INODE_BLOCK, inodesBlock);
        var location = inodeNumber * INODES_SIZE;
        byte[] bufferValid = Arrays.copyOfRange(inodesBlock, location, location + INT_SIZE);
        byte[] bufferSize = Arrays.copyOfRange(inodesBlock, location + INT_SIZE, location + INT_SIZE * 2);
        byte[] bufferDirect = Arrays.copyOfRange(inodesBlock, location + INT_SIZE * 2, location + INODES_SIZE);
        int valid = ByteBuffer.wrap(bufferValid).getInt();
        int size = ByteBuffer.wrap(bufferSize).getInt();
        int[] direct = new int[bufferDirect.length / INT_SIZE];
        for (int i = 0; i < direct.length; i++) {
            direct[i] = ByteBuffer.wrap(Arrays.copyOfRange(bufferDirect, i * INT_SIZE, (i + 1) * INT_SIZE)).getInt();
        }
        return new Inode(size, direct);
    }


    // public int findsInodeIdAvailable() {
    //     final var readBlock = readBlock(m_disk, 2);
    //     final var filesNamesInodes = new String(readBlock, StandardCharsets.UTF_8).trim().split(SEPARATOR);
    //     final var fileNames = new ArrayList<Integer>();
    //     for (int i = 0; i < filesNamesInodes.length - 1; i += 2) {
    //         fileNames.add(Integer.parseInt(filesNamesInodes[i + 1])); // todo see
    //     }
    //     int i = 0;
    //     for (; i < m_totalInodes; ++i) {
    //         if (!fileNames.contains(i)) {
    //             assert i != 0;
    //             return i;
    //         }
    //     }
    //     throw new RuntimeException("No Inode Available");
    // }
    //
    // private byte[] readBlock(final IoOperations disk, final int numBlocks) {
    //     final byte[] inodesBlock = new byte[m_blockSize];
    //     disk.read(numBlocks, inodesBlock);
    //     return inodesBlock;
    //
    // }

}


/*
    private byte[] paddingWithZero(final String str, final int size) {
        final byte[] paddedFileName = Arrays.copyOf(str.getBytes(), size);
        return paddedFileName;
    }

    private void writeDotFile2(final IoOperations disk, final byte[] data) {

        final byte[] fileName = paddingWithZero(".", FILE_NAME_SIZE); // 16
        final byte[] fileId = paddingWithZero("0", INDO_NAME_SIZE); // 4


        // dataName = dataString.getBytes();

        Arrays.fill(data, (byte) 0);

        System.arraycopy(dataString.getBytes(StandardCharsets.UTF_8),
                0, data, 0, dataString.length());

        final File file = new File(fileName, 0, data);
        writeBlock(disk, file, 2);

    }*/
