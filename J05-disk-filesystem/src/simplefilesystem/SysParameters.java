package simplefilesystem;

public final class SysParameters {
    public static final int BLOCK_SIZE = 128; // 4000
    public static final int NUM_BLOCKS = 10;

    public static final int SUPER_BLOCK = 0;
    public static final int INODE_BLOCK = 1;
    public static final int DOT_BLOCK = 2;

    public static final String ROOT_NAME_FILE = ".";
    public static final String SEPARATOR = ":";
    public static final int INDEX_ROOT_FILE = 0;
    public static final String INVALID_CHARS = "[\\\\/:*?\"<>|]";

    public static final int SIZE_BLOCK = 4;
    public static final int INT_SIZE = 4;
    public static final int BYTE_SIZE = 2;
}
