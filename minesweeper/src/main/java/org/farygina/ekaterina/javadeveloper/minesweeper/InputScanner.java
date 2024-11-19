package org.farygina.ekaterina.javadeveloper.minesweeper;

import java.util.Scanner;

public class InputScanner {
    private final Scanner scanner;

    InputScanner() {
        this.scanner = new Scanner(System.in);
    }

    public int readNumber() {
        System.out.print("How many mines do you want on the field? ");
        while (true) {
            try {
                return Integer.parseInt(this.scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("It's not a number! Please try again");
            }
        }
    }

    public String[] readCoordinatesAndCommand() {
        String[] result;
        System.out.print("Set/unset mines marks or claim a cell as free: ");
        while (true) {
            result = this.scanner.nextLine().split(" ");
            if (result.length != 3) {
                System.out.println("Invalid command");
                continue;
            }
            result[2] = result[2].toLowerCase();
            if (!("free".equals(result[2]) || "mine".equals(result[2]))) {
                System.out.println("Invalid command");
                continue;
            }
            try {
                int x = Integer.parseInt(result[0]) - 1;
                int y = Integer.parseInt(result[1]) - 1;
                if (x < 0 || x > 8 || y < 0 || y > 8) {
                    throw new NumberFormatException();
                }
                result[0] = String.valueOf(x);
                result[1] = String.valueOf(y);
                break;
            } catch (NumberFormatException e) {
                System.out.println("It's not a number! Please try again");
            }
        }
        return result;
    }
}
