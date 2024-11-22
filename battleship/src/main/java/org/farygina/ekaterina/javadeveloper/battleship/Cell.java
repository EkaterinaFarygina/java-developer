package org.farygina.ekaterina.javadeveloper.battleship;

class Cell {
    private CellState state;

    Cell() {
        this.state = CellState.FOG;
    }

    void setState(CellState state) {
        this.state = state;
    }

    CellState getState() {
        return this.state;
    }

    @Override
    public String toString() {
        return this.state.toString();
    }
}
