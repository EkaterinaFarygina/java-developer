package org.farygina.ekaterina.javadeveloper.chuckNorrisCipherEncoder;

import java.util.Scanner;

public class InputScanner {
    private final Scanner scanner;

    InputScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    String readInputToEncode() {
        System.out.println("Input string:");
        return this.scanner.nextLine();
    }

    String readInputToDecode() {
        System.out.println("Input encoded string:");
        return this.scanner.nextLine();
    }

    Command readCommand() {
        while(true) {
            System.out.println("Please input operation (encode/decode/exit):");
            String input = scanner.nextLine();
            try {
                return Command.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.printf("There is no '%s' operation\n\n", input);
            }
        }
    }
}
