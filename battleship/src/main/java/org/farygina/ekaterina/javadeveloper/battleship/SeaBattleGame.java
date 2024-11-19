package org.farygina.ekaterina.javadeveloper.battleship;

import java.util.Scanner;

public class SeaBattleGame {
    private final Board board1;
    private final Board board2;
    private final InputScanner scanner;
    private int successfulShots1;
    private int successfulShots2;

    SeaBattleGame() {
        this.board1 = new Board();
        this.board2 = new Board();
        this.scanner = new InputScanner(new Scanner(System.in));
        this.successfulShots1 = 0;
        this.successfulShots2 = 0;
    }

    private void printBoardsForShooting(boolean isPlayer1Turn) {
        if (isPlayer1Turn) {
            board1.setGameOn(false);
            board2.setGameOn(true);
        } else {
            board1.setGameOn(true);
            board2.setGameOn(false);
        }
        System.out.print(isPlayer1Turn ? board2 : board1);
        System.out.println("---------------------");
        System.out.println(isPlayer1Turn ? board1 : board2);
    }

    private void fillBoard(boolean isPlayer1) {
        AddShipResult result;
        System.out.println(isPlayer1 ? this.board1 : this.board2);
        for (ShipType type : ShipType.values()) {
            System.out.printf("Enter the coordinates of the %s (%d cells):\n\n", type.getName(), type.getLength());
            result = null;
            while (result != AddShipResult.SUCCESS) {
                String[] coordinates = scanner.readCoordinates();
                result = isPlayer1 ? this.board1.addShip(type, coordinates[0], coordinates[1])
                        : this.board2.addShip(type, coordinates[0], coordinates[1]);
                switch (result) {
                    case WRONG_SHIP_LENGTH_ERROR ->
                            System.out.printf("Error! Wrong length of the %s! Try again:\n\n", type.getName());
                    case WRONG_SHIP_LOCATION_ERROR -> System.out.println("Error! Wrong ship location! Try again:\n");
                    case CLOSE_TO_ANOTHER_SHIP_ERROR ->
                            System.out.println("Error! You placed it too close to another one. Try again:\n");
                }
            }
            System.out.println(isPlayer1 ? this.board1 : this.board2);
        }
    }

    private void shoot(boolean isPlayer1Turn) {
        CellState state = CellState.FOG;
        System.out.println(isPlayer1Turn ? "Player 1, it's your turn:" : "Player 2, it's your turn:");
        while (state == CellState.FOG) {
            if (isPlayer1Turn) {
                state = this.board2.shoot(scanner.readCoordinate());
            } else {
                state = this.board1.shoot(scanner.readCoordinate());
        }
        if (state == CellState.SHIP_HIT) {
            if (isPlayer1Turn) {
                this.successfulShots1++;
            } else {
                this.successfulShots2++;
            }
        }
    }
}

    private void passMove() {
        System.out.println("Press Enter and pass the move to another player");
        if (scanner.readEnter()) {
            try {
                if (System.getProperty("os.name").contains("Windows")) {
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                } else {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void gaming() {
        System.out.println("Player 1, place your ships on the game field");
        fillBoard(true);
        passMove();
        System.out.println("Player 2, place your ships on the game field");
        fillBoard(false);
        passMove();
        boolean isPlayer1Turn = true;
        while (true) {
            printBoardsForShooting(isPlayer1Turn);
            shoot(isPlayer1Turn);
            if (successfulShots1 == 17 || successfulShots2 == 17) {
                break;
            }
            passMove();
            isPlayer1Turn = !isPlayer1Turn;
        }
        System.out.println("You sank the last ship. You won. Congratulations!");
        System.out.println(this.successfulShots1 == 17 ? "Player 1 won!" : "Player 2 won!");
    }
}
