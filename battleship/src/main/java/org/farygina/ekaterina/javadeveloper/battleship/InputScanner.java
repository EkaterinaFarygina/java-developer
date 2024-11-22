package org.farygina.ekaterina.javadeveloper.battleship;

import java.util.Scanner;

class InputScanner {
    private final Scanner scanner;

    InputScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    String[] readCoordinates() {
        String[] result;
        while(true) {
            result = this.scanner.nextLine().split(" ");
            if (result.length == 2) {
                return result;
            } else {
                System.out.println("Wrong input! Try again:");
            }
        }
    }

    String readCoordinate() {
        while (true) {
            String input = this.scanner.nextLine();
            if (input.length() == 2 || input.length() == 3) {
                if (input.charAt(0) >= 'A' && input.charAt(0) <= 'J') {
                    try {
                        int x = Integer.parseInt(input.substring(1));
                        if (x > 0 && x < 11) {
                            return input;
                        }
                    } catch (NumberFormatException ignored) {}
                }
            }
            System.out.println("Error! You entered the wrong coordinates! Try again:");
        }
    }

    boolean readEnter() {
        return !this.scanner.nextLine().isEmpty();
    }
}