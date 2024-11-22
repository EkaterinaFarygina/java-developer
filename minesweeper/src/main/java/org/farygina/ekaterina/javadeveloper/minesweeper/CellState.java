package org.farygina.ekaterina.javadeveloper.minesweeper;

enum CellState {
    MARK("*"),
    EXPLORED_FREE("/"),
    EMPTY(".");
    private final String text;

    CellState(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
