package org.farygina.ekaterina.javadeveloper.minesweeper;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

import static org.farygina.ekaterina.javadeveloper.minesweeper.CellState.*;

public class MineField {
    private final Cell[][] cells;
    private int mines;
    private final InputScanner scanner;

    MineField() {
        this.scanner = new InputScanner();
        this.cells = new Cell[9][9];
        this.mines = new InputScanner().readNumber();
        initializeField();
        initializeMines();
        calculateMinesAroundEachCell();
    }

    void initializeField() {
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                this.cells[i][j] = new Cell(EMPTY, j, i);
            }
        }
    }

    void initializeMines() {
        int minesToCreate = this.mines;
        while (minesToCreate != 0) {
            if (putMine()) {
                minesToCreate--;
            }
        }
    }

    boolean putMine() {
        var random = new Random();
        var randomY = random.nextInt(9);
        var randomX = random.nextInt(9);
        if (!cells[randomY][randomX].isMine()) {
            cells[randomY][randomX].setMine(true);
            return true;
        } else {
            return false;
        }
    }

    void calculateMinesAroundEachCell() {
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[i].length; j++) {
                calculateMinesAroundCell(j, i);
            }
        }
    }

    void calculateMinesAroundCell(int x, int y) {
        var minesCount = 0;
        for (int i = Math.max(y - 1, 0); i <= Math.min(y + 1, this.cells.length - 1); i++) {
            for (int j = Math.max(x - 1, 0); j <= Math.min(x + 1, this.cells[y].length - 1); j++) {
                if (i != y || j != x) {
                    if (this.cells[i][j].isMine()) {
                        minesCount++;
                    }
                }
            }
        }
        this.cells[y][x].setMinesAround(minesCount);
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();
        builder.append(" |123456789|\n");
        builder.append("-|---------|\n");
        for (int i = 0; i < this.cells.length; i++) {
            builder.append(i + 1);
            builder.append("|");
            for (Cell cell : this.cells[i]) {
                if (cell.isOpen()) {
                    if (cell.getMinesAround() > 0) {
                        builder.append(cell.getMinesAround());
                    } else {
                        builder.append(cell.getState());
                    }
                } else if (cell.getState() == MARK) {
                    builder.append(MARK);
                } else {
                    builder.append(EMPTY);
                }
            }
            builder.append("|\n");
        }
        builder.append("-|---------|");
        return builder.toString();
    }

    private void printFailedGame() {
        var builder = new StringBuilder();
        builder.append(" |123456789|\n");
        builder.append("-|---------|\n");
        for (int i = 0; i < this.cells.length; i++) {
            builder.append(i + 1);
            builder.append("|");
            for (Cell cell : this.cells[i]) {
                if (cell.isMine()) {
                    builder.append("X");
                } else if (cell.isOpen()) {
                    if (cell.getMinesAround() > 0) {
                        builder.append(cell.getMinesAround());
                    } else {
                        builder.append(cell.getState());
                    }
                } else {
                    builder.append(EMPTY);
                }
            }
            builder.append("|\n");
        }
        builder.append("-|---------|");
        System.out.println(builder);
        System.out.println("You stepped on a mine and failed!");
    }

    private void exploreNeighbours(Cell cell) {
        Queue<Cell> queue = new ArrayDeque<>();
        queue.add(cell);
        while (!queue.isEmpty()) {
            var current = queue.poll();
            if (current.getMinesAround() == 0) {
                var x = current.getX();
                var y = current.getY();
                for (int i = Math.max(y - 1, 0); i <= Math.min(y + 1, this.cells.length - 1); i++) {
                    for (int j = Math.max(x - 1, 0); j <= Math.min(x + 1, this.cells[y].length - 1); j++) {
                        if (!this.cells[i][j].isOpen()) {
                            queue.add(this.cells[i][j]);
                        }
                    }
                }
            }
            current.setOpen(true);
            current.setState(EXPLORED_FREE);
        }
        System.out.println(this);
    }

    private void firstMove(int x, int y) {
        var cell = this.cells[y][x];
        if (cell.isMine()) {
            cell.setMine(false);
            boolean replacedSuccessfully = false;
            while (!replacedSuccessfully) {
                replacedSuccessfully = putMine();
            }
            calculateMinesAroundEachCell();
        }
        exploreNeighbours(cell);
        cell.setOpen(true);
    }

    public void gaming() {
        var marks = 0;
        var isFirstFree = true;
        System.out.println(this);
        while (this.mines != 0 && marks != this.mines) {
            var input = this.scanner.readCoordinatesAndCommand();
            var x = Integer.parseInt(input[0]);
            var y = Integer.parseInt(input[1]);
            var command = Command.valueOf(input[2].toUpperCase());
            var cell = this.cells[y][x];
            if (cell.isOpen() && (cell.getState() == EXPLORED_FREE || cell.getMinesAround() > 0)) {
                System.out.println("This cell was already explored!");
                continue;
            }
            switch (command) {
                case FREE -> {
                    if (isFirstFree) {
                        firstMove(x, y);
                        isFirstFree = false;
                        continue;
                    } else {
                        if (cell.isMine()) {
                            printFailedGame();
                            return;
                        } else {
                            if (cell.getMinesAround() == 0) {
                                exploreNeighbours(cell);
                            }
                            cell.setOpen(true);
                        }
                    }
                }
                case MINE -> {
                    if (cell.getState() == MARK) {
                        if (cell.isMine()) {
                            this.mines++;
                        }
                        cell.setState(EMPTY);
                        marks--;
                    } else {
                        if (cell.isMine()) {
                            this.mines--;
                        }
                        cell.setState(MARK);
                        marks++;
                    }
                }
            }
            System.out.println(this);
        }
        System.out.println("Congratulations! You found all the mines!");
    }
}
