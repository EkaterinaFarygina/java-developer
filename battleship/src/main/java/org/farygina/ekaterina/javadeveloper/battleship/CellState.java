package org.farygina.ekaterina.javadeveloper.battleship;

enum CellState {
    FOG("~"),
    SHIP("O"),
    SHIP_HIT("X"),
    MISS("M");
    private final String text;

    CellState(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
