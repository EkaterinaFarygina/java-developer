package org.farygina.ekaterina.javadeveloper.asciimirror;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        var scanner = new InputScanner(new Scanner(System.in));
        String filePath = scanner.inputFilePath();
        ASCIIMirror mirror;
        try {
            mirror = new ASCIIMirror(filePath);
            mirror.printMirror();
        } catch (IOException e1) {
            System.out.println("File not found!");
        }
    }
}