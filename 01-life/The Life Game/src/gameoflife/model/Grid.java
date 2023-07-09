package gameoflife.model;

import java.util.function.Supplier;

public final class Grid<T> {
    private final T[][] matrix;
    private final int m_width;
    //private Predicate<Void> m_predicateCellLife; if we use Void -> use Supplier
    private final int m_height;

    @SuppressWarnings("unchecked")
    public Grid(final int height, final int width, final Supplier<T> supplierCellIsLife) {
        m_height = height;
        m_width = width;

        final var m = new Object[m_width][m_height];
        matrix = (T[][]) m;

        for (int row = 0; row < m_width; ++row) {
            for (int col = 0; col < m_height; ++col) {
                matrix[row][col] = (T) supplierCellIsLife.get();
            }
        }
    }


    public int getWidth() {
        return m_width;
    }

    public int getHeight() {
        return m_height;
    }

    public void set(final int x, final int y, final T value) {
        matrix[x][y] = value;
    }

    public T get(final int x, final int y) {
        return matrix[x][y];
    }


}
