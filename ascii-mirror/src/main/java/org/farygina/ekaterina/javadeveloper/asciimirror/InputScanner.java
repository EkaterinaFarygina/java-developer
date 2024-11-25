package org.farygina.ekaterina.javadeveloper.asciimirror;

import java.util.Scanner;

public class InputScanner {
    private final Scanner scanner;

    InputScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public String inputFilePath() {
        System.out.println("Input the file path:");
        return this.scanner.nextLine();
    }
}
