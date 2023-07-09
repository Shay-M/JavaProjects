package gameoflife.rules;

import gameoflife.model.Grid;

public class ClassicLifeRules implements GameRules<Boolean> {
    private static final int[][] NEIGHBOR_WINDOW = {
            {-1, -1},
            {-1, 0},
            {-1, 1},
            {0, -1},
            {0, 1},
            {1, -1},
            {1, 0},
            {1, 1}
    };

    @Override
    public Boolean nextStage(final int x, final int y, final Grid<Boolean> currentGeneration) {
        int numNeighbors = countLiveNeighbors(x, y, currentGeneration);

        if (currentGeneration.get(x, y)) {
            // Any 'live' cell with two or three live neighbours survives.
            return numNeighbors == 2 || numNeighbors == 3;
        }
        else {
            // Any 'dead' cell with three live neighbours becomes a live cell.
            return numNeighbors == 3;
        }

    }

    private int countLiveNeighbors(final int row, final int col, final Grid<Boolean> currentGeneration) {
        final var width = currentGeneration.getWidth();
        final var height = currentGeneration.getHeight();
        int count = 0;
        for (int[] offset : NEIGHBOR_WINDOW) {
            final int neighborRow = row + offset[0];
            final int neighborCol = col + offset[1];

            if (neighborRow >= 0 && neighborRow < width && // outside 'width' bound of the grid
                    neighborCol >= 0 && neighborCol < height && // outside 'height' bound of the grid
                    ((boolean) currentGeneration.get(neighborRow, neighborCol))) // now position is alive   // ?
            {
                ++count;
            }
        }
        return count;
    }
}
