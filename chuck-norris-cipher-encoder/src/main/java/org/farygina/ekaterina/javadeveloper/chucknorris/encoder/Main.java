package org.farygina.ekaterina.javadeveloper.chucknorris.encoder;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        startEncoder(new ChuckNorrisCipherEncoder(), new InputScanner(new Scanner(System.in)));
    }

    private static void startEncoder(ChuckNorrisCipherEncoder encoder, InputScanner scanner) {
        Command command = null;
        while (command != Command.EXIT) {
            command = scanner.readCommand();
            switch (command) {
                case DECODE -> {
                    var result = encoder.decode(scanner.readInputToDecode());
                    System.out.println(
                            result.isEmpty()
                                    ? "Encoded string is not valid.\n"
                                    : "Decoded string:\n" + encoder.decode(scanner.readInputToDecode())
                    );
                }
                case ENCODE -> {
                    var result = encoder.encode(scanner.readInputToEncode());
                    System.out.println(result.isEmpty() ? "String is not valid.\n" : "Encoded string:\n" + result);
                }
                case EXIT -> System.out.println("Bye!");
            }
        }
    }
}