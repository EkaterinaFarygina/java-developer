package org.farygina.ekaterina.javadeveloper.minesweeper;

public class Cell {
    private CellState state;
    private final int x;
    private final int y;
    private int minesAround;
    private boolean isOpen;
    private boolean isMine;

    Cell(CellState state, int x, int y) {
        this.state = state;
        this.x = x;
        this.y = y;
        this.minesAround = 0;
        this.isOpen = false;
        this.isMine = false;
    }

    CellState getState() {
        return this.state;
    }
    int getX() {
        return this.x;
    }

    int getY() {
        return this.y;
    }

    void setState(CellState state) {
        this.state = state;
    }

    int getMinesAround() {
        return this.minesAround;
    }

    void setMinesAround(int minesAround) {
        this.minesAround = minesAround;
    }

    boolean isOpen() {
        return this.isOpen;
    }

    void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    boolean isMine() {
        return this.isMine;
    }

    void setMine(boolean isMine) {
        this.isMine = isMine;
    }

    @Override
    public String toString() {
        return this.state.toString();
    }
}
