package simplefilesystem.inodes;

import java.util.ArrayList;
import java.util.List;

// 32b
public class Inode {
    private static final int InodeSize = 5;
    private int m_valid = 0;
    private int m_size = 0;
    private int count = 0;
    private int[] m_direct = new int[InodeSize];
    // private List<Integer> m_direct = new ArrayList<>(InodeSize);
    private int m_indirect = 0;

    public Inode() {
    }

    public Inode(final int size, final int direct) {
        m_valid = 1;
        m_size = size;
        addDirect(direct);
    }

    public Inode(final int size, final int[] directs) {
        m_valid = 1;
        m_size = size;
        m_direct = directs;
    }


    public int getValid() {
        return m_valid;
    }

    public void setValid(final int valid) {
        this.m_valid = valid;
    }

    public int getSize() {
        return m_size;
    }

    public void setSize(final int size) {
        this.m_size = size;
    }

    public int[] getDirect() {

        return m_direct;
    }

    public List<Integer> getDirectAsList() {
        List<Integer> direct = new ArrayList<>(InodeSize);
        for (var d : m_direct) {
            direct.add(d);
        }
        return direct;
    }

    public boolean addDirect(final int direct) {
        if (m_direct.length + 1 == InodeSize) {
            return false;
        }
        else {
            m_direct[count] = direct;
            ++count;

        }
        return false;
    }

    public int getIndirectNum() {
        return m_indirect;
    }

    public void setIndirect(final int indirect) {
        this.m_indirect = indirect;
    }

    public void setDirects(final List<Integer> directsFromInode) {
        for (int i = 0; i < InodeSize; ++i) {
            m_direct[i] = directsFromInode.get(i);
        }

    }
}
