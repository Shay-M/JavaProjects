package simplefilesystem;

import simplefilesystem.inodes.Inode;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static simplefilesystem.SysParameters.*;

public class File {


    private String m_fileName;
    private final FileSystem m_fileSystem;
    private final Inode m_inode;
    private final int m_inodeNumber;

    private int m_position = 0;

    public Inode getInode() {
        return m_inode;
    }

    public String getFileName() {
        return m_fileName;
    }

    public File(final String fileName, final FileSystem fileSystem, final Inode inode, final int inodeNumber) {
        m_fileName = fileName;
        m_fileSystem = fileSystem;
        // getIndirect(inode.getIndirectNum());
        m_inode = inode;
        m_inodeNumber = inodeNumber;
    }

    // private void getIndirect(final int indirectNum) {
    //     if (indirectNum > 0) {
    //         var blockBuffer = m_fileSystem.readBlock(indirectNum);
    //         var byteBuffer = ByteBuffer.wrap(blockBuffer).asIntBuffer();
    //
    //         while (byteBuffer.hasRemaining()) {
    //             m_blocksNumbers.add(byteBuffer.get());
    //         }
    //     }
    // }

    public final void setPosition(final int offset) {
        m_position = offset;
        // if (offset < 0) {
        //     m_position = 0;
        // }
        // else if (offset > m_inode.getSize()) {
        //     m_position = m_inode.getSize();
        // }
        // else {
        //     m_position = offset;
        // }

    }

    public final int getPosition() {
        return m_position;
    }

    public final String readString() {
        final var byteBuffer = ByteBuffer.allocate(BLOCK_SIZE);
        m_fileSystem.read(m_inode, m_position, byteBuffer);
        byteBuffer.flip();
        final CharBuffer charBuffer = StandardCharsets.UTF_8.decode(byteBuffer);
        final var chars = charBuffer.array();
        final StringBuilder stringResult = new StringBuilder();

        for (int i = m_position; i < chars.length; ++i) {
            if (chars[i] == '\n') {
                break;
            }
            stringResult.append(chars[i]);
        }

        m_position += stringResult.length() + 1;
        return stringResult.toString();
        // final var byteBuffer = ByteBuffer.allocate(BLOCK_SIZE);
        // m_fileSystem.read(m_inode, m_position, byteBuffer);
        // byteBuffer.flip();
        // final CharBuffer charBuffer = StandardCharsets.UTF_8.decode(byteBuffer);
        // final String result = charBuffer.toString().trim().split("\n")[m_position];
        // // m_position += result.getBytes(StandardCharsets.UTF_8).length;
        // // m_position += result.length() * 2;
        // m_position += 1;
        // return result;

    }


    public final void write(final String stringToWrite) {
        Objects.requireNonNull(stringToWrite);
        if (!stringToWrite.isEmpty()) {
            final ByteBuffer encode = Charset.defaultCharset().encode(stringToWrite + '\n');
            m_fileSystem.write(m_inode, m_position, encode, m_inodeNumber);
            m_position += stringToWrite.length() + 1; // 1 for '\n'
        }
    }

    public final int readInteger() {
        final var byteBuffer = ByteBuffer.allocate(BLOCK_SIZE);
        m_fileSystem.read(m_inode, m_position, byteBuffer);
        final var result = byteBuffer.getInt(m_position);
        m_position += INT_SIZE;
        return result;
    }

    public final void write(final int intToWrite) {
        final ByteBuffer buffer = ByteBuffer.allocate(BLOCK_SIZE);
        buffer.putInt(intToWrite);
        m_fileSystem.write(m_inode, m_position, buffer, m_inodeNumber);
        m_position += INT_SIZE;
    }

    public final byte readBytes() {
        final var byteBuffer = ByteBuffer.allocate(BLOCK_SIZE);
        m_fileSystem.read(m_inode, m_position, byteBuffer);
        m_position += BYTE_SIZE;
        return 0; // todo
    }

    public final void write(byte[] bytesToWrite) {
        m_fileSystem.write(m_inode, m_position, ByteBuffer.wrap(bytesToWrite), m_inodeNumber);
    }


}
