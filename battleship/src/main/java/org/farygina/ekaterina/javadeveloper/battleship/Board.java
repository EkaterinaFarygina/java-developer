package org.farygina.ekaterina.javadeveloper.battleship;

public class Board {
    private final Cell[][] cells = new Cell[10][10];
    private boolean isGameOn;

    Board() {
        initialiseBoard();
        this.isGameOn = false;
    }

    void setGameOn(boolean isGameOn) {
        this.isGameOn = isGameOn;
    }

    private void initialiseBoard() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                this.cells[i][j] = new Cell();
            }
        }
    }

    private int[] parseCoordinate(String coordinate) {
        int y = coordinate.charAt(0) - 65;
        int x = Integer.parseInt(coordinate.substring(1)) - 1;
        return new int[]{x, y};
    }

    private boolean isCoordinatesCorrect(int x1, int y1, int x2, int y2) {
        if (x1 != -1 || y1 != -1 || x2 != -1 || y2 != -1) {
            return ((x2 - x1) == 0) || ((y2 - y1) == 0);
        } else {
            return false;
        }
    }

    private boolean isLengthCorrect(int requiredLength, int x1, int y1, int x2, int y2) {
        int dif = (x2 - x1) == 0 ? Math.abs(y2 - y1) : Math.abs(x2 - x1);
        return requiredLength == dif + 1;
    }

    private boolean isTouchingNeighbourShip(int x1, int y1, int x2, int y2) {
        int left = x1 < x2 ? Math.max(x1 - 1, 0) : Math.max(x2 - 1, 0);
        int right = x1 > x2 ? Math.min(x1 + 1, 9) : Math.min(x2 + 1, 9);
        int up = y1 < y2 ? Math.max(y1 - 1, 0) : Math.max(y2 - 1, 0);
        int down = y1 > y2 ? Math.min(y1 + 1, 9) : Math.min(y2 + 1, 9);
        for (int i = up; i <= down; i++) {
            for (int j = left; j <= right; j++) {
                if (cells[i][j].getState() != CellState.FOG) {
                    return true;
                }
            }
        }
        return false;
    }

    private void placeShip(int x1, int y1, int x2, int y2) {
        int startX = Math.min(x1, x2);
        int endX = Math.max(x1, x2);
        int startY = Math.min(y1, y2);
        int endY = Math.max(y1, y2);
        for (int i = startY; i <= endY; i++) {
            for (int j = startX; j <= endX; j++) {
                cells[i][j].setState(CellState.SHIP);
            }
        }
    }

    public AddShipResult addShip(ShipType shipType, String position1, String position2) {
        int[] coordinate1 = parseCoordinate(position1);
        int[] coordinate2 = parseCoordinate(position2);
        if(!isCoordinatesCorrect(coordinate1[0], coordinate1[1], coordinate2[0], coordinate2[1])) {
            return AddShipResult.WRONG_SHIP_LOCATION_ERROR;
        }
        if(isTouchingNeighbourShip(coordinate1[0], coordinate1[1], coordinate2[0], coordinate2[1])) {
            return AddShipResult.CLOSE_TO_ANOTHER_SHIP_ERROR;
        }
        if(!isLengthCorrect(shipType.getLength(), coordinate1[0], coordinate1[1], coordinate2[0], coordinate2[1])) {
            return AddShipResult.WRONG_SHIP_LENGTH_ERROR;
        }
        placeShip(coordinate1[0], coordinate1[1], coordinate2[0], coordinate2[1]);
        return AddShipResult.SUCCESS;
    }

    private boolean isShipSank(int x, int y) {
        CellState state;
        int coordY = y - 1;
        while(coordY >= 0) {
            state = cells[coordY][x].getState();
            switch (state) {
                case SHIP -> {
                    return false;
                }
                case SHIP_HIT -> coordY--;
                case FOG, MISS -> coordY = -1;
            }
        }
        coordY = y + 1;
        while(coordY < 10) {
            state = cells[coordY][x].getState();
            switch (state) {
                case SHIP -> {
                    return false;
                }
                case SHIP_HIT -> coordY++;
                case FOG, MISS -> coordY = 10;
            }
        }
        int coordX = x - 1;
        while(coordX >= 0) {
            state = cells[y][coordX].getState();
            switch (state) {
                case SHIP -> {
                    return false;
                }
                case SHIP_HIT -> coordX--;
                case FOG, MISS -> coordX = -1;
            }
        }
        coordX = x + 1;
        while(coordX < 10) {
            state = cells[y][coordX].getState();
            switch (state) {
                case SHIP -> {
                    return false;
                }
                case SHIP_HIT -> coordX++;
                case FOG, MISS -> coordX = 10;
            }
        }
        return true;
    }

    public CellState shoot(String position) {
        int[] coordinate = parseCoordinate(position);
        int x = coordinate[0];
        int y = coordinate[1];
        CellState state = cells[y][x].getState();
        switch(state) {
            case FOG -> {
                System.out.println("You missed!\n");
                cells[y][x].setState(CellState.MISS);
                return CellState.MISS;
            }
            case SHIP -> {
                if (isShipSank(x, y)) {
                    System.out.println("You sank a ship!");
                } else {
                    System.out.println("You hit a ship!\n");
                }
                cells[y][x].setState(CellState.SHIP_HIT);
                return CellState.SHIP_HIT;
            }
            default -> {
                System.out.println("You already hit this spot!\n");
                return CellState.MISS;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("  1 2 3 4 5 6 7 8 9 10\n");
        for (int i = 0; i < cells.length; i++) {
            builder.append((char) (65 + i));
            for (int j = 0; j < cells[i].length; j++) {
                builder.append(" ");
                if (isGameOn && this.cells[i][j].getState() == CellState.SHIP) {
                    builder.append(CellState.FOG);
                } else {
                    builder.append(this.cells[i][j]);
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
