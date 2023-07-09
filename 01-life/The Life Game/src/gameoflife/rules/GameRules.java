package gameoflife.rules;

import gameoflife.model.Grid;

public interface GameRules<T> {
    T nextStage(final int x, final int y, final Grid<T> nowGrid);
}
