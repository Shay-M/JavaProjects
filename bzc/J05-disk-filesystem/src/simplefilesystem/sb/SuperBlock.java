package simplefilesystem.sb;

// 16b
// public record SuperBlock(int magicNumber, int blocks, int inodeBlocks, int inodes) {
//     public boolean isValid() {
//         return true;
//     }
//
// }
// (int magicNumber, int blocks, int inodeBlocks, int inodes)

import java.nio.ByteBuffer;
import java.util.Arrays;

public class SuperBlock {
    private static final int ID_MAGIC_NUMBER = 0xC0DEBABE;

    private int m_magicNumber;
    private int m_blocks;
    private int m_inodeBlocks; //כמות בלוקים
    private int m_inodes; // כמה יש כולל

    public SuperBlock(final byte[] bufferSuperBlock) {
        m_magicNumber = parserSuperBlock(bufferSuperBlock, 0, 4);
        m_blocks = parserSuperBlock(bufferSuperBlock, 4, 8);
        m_inodeBlocks = parserSuperBlock(bufferSuperBlock, 8, 12);
        m_inodes = parserSuperBlock(bufferSuperBlock, 12, 16);
    }

    public SuperBlock(int magicNumber, int blocks, int inodeBlocks, int inodes) {
        m_magicNumber = magicNumber;
        m_blocks = blocks;
        m_inodeBlocks = inodeBlocks;
        m_inodes = inodes;

    }

    private int parserSuperBlock(final byte[] bufferSuperBlock, final int start, final int end) {
        var arr = Arrays.copyOfRange(bufferSuperBlock, start, end);
        return ByteBuffer.wrap(arr).getInt();
    }

    public boolean isValid() {
        return m_magicNumber == ID_MAGIC_NUMBER;
    }

    public int getMagicNumber() {
        return m_magicNumber;
    }

    public int getBlocks() {
        return m_blocks;
    }

    public int getInodeBlocks() {
        return m_inodeBlocks;
    }

    public int getInodes() {
        return m_inodes;
    }

}




