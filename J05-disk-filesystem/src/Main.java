/*
This project consists of designing and implementing a simple file system on top
of a simulated disk.


hardware.DiskController.java : represent a disk controller that is responsible for all operations on a disk.
it has the following operations:
    0. Constructor that sets the path where all disk files will be saved.
    1.  hardware.Disk get(int diskNumber)  - gets the object handling said disk.  hardware.Disk number x will be saved
        using file named "disk-01.dsk". create that file if it doesn't exist.
    2. int shutdown()  close all disk objects
    3. int count() return the number of currently create disks.

Note
1. hardware.Disk must be thread safe as
2. hardware.DiskController shall behave as singleton.

Phase 2:
upgrade hardware.Disk class to use Files.newByteChannel(...) function to read/write from disk

make sure you have unit tests.

Phase 3:
Implement a file system emulation on top of the previous phase.
see attached document.
*/

import hardware.DiskController;
import hardware.IoOperations;
import simplefilesystem.FileSystem;

import java.nio.ByteBuffer;
import java.nio.file.Path;

import static simplefilesystem.SysParameters.BLOCK_SIZE;
import static simplefilesystem.SysParameters.NUM_BLOCKS;

public class Main {
    public static void main(final String[] args) {

    }

    private int[] parserSuperBlock(final byte[] bufferSuperBlock) {
        assert bufferSuperBlock.length == 4;
        int[] result = new int[4];
        for (int i = 0; i < bufferSuperBlock.length; ++i) {
            for (int j = 0; j < 8; j += 4) {
                int index = (i * 8 + j) / 4;
                int num = (bufferSuperBlock[i] >> j) & 0x0F;
                result[index] = num;
            }
        }
        return result;
    }

}

