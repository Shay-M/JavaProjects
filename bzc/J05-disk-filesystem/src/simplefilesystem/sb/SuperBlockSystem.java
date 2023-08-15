package simplefilesystem.sb;

import hardware.IoOperations;
import simplefilesystem.SysParameters;

import java.nio.ByteBuffer;

public final class SuperBlockSystem {
    private static final int INT_SIZE = 4;
    private static final int ID_MAGIC_NUMBER = 0xC0DEBABE;
    private static final double PRES_OF_INODE_BLOCKS = 0.1;
    private static final int INODES_SIZE = 32;
    private final IoOperations m_disk;
    private final int m_blockSize;

    public SuperBlockSystem(final IoOperations disk) {
        m_disk = disk;
        m_blockSize = disk.getBlockSize();
    }

    public SuperBlock createSuperBlock() {
        final var numberOfBlocksForInodes = (int) Math.ceil(m_disk.getNumBlocks() * PRES_OF_INODE_BLOCKS);
        final SuperBlock superBlock = new SuperBlock(
                ID_MAGIC_NUMBER,
                m_disk.getNumBlocks(),
                numberOfBlocksForInodes,
                m_blockSize * numberOfBlocksForInodes / INODES_SIZE
        );
        return superBlock;
    }

    public void writeSuperBlock(final SuperBlock superBlock) {
        byte[] buffer = new byte[m_blockSize];
        final var bufferMagicNumber = ByteBuffer.allocate(INT_SIZE).putInt(superBlock.getMagicNumber()).array();
        final var bufferBlocks = ByteBuffer.allocate(INT_SIZE).putInt(superBlock.getBlocks()).array();
        final var bufferInodeBlock = ByteBuffer.allocate(INT_SIZE).putInt(superBlock.getInodeBlocks()).array();
        final var bufferInodes = ByteBuffer.allocate(INT_SIZE).putInt(superBlock.getInodes()).array();

        System.arraycopy(bufferMagicNumber, 0, buffer, 0, bufferMagicNumber.length);
        System.arraycopy(bufferBlocks, 0, buffer, bufferMagicNumber.length, bufferBlocks.length);
        System.arraycopy(bufferInodeBlock, 0, buffer, bufferMagicNumber.length + bufferBlocks.length, bufferInodeBlock.length);
        System.arraycopy(bufferInodes, 0, buffer, bufferMagicNumber.length + bufferBlocks.length + bufferInodeBlock.length, bufferInodes.length);

        // var mn = superBlock.magicNumber();
        // byte mask = (byte) 0xFF;
        // byte[] buffer = new byte[m_blockSize];
        // buffer[0] = (byte) ((mn >> 24) & mask);
        // buffer[1] = (byte) ((mn >> 16) & mask);
        // buffer[2] = (byte) ((mn >> 8) & mask);
        // buffer[3] = (byte) (mn & mask);

        // buffer[4] = (byte) ((superBlock.blocks() >> 24) & mask) ;
        // buffer[5] = (byte) superBlock.inodeBlocks();
        // buffer[6] = (byte) superBlock.inodes();

        m_disk .write(SysParameters.SUPER_BLOCK, buffer);
    }
}
