package org.farygina.ekaterina.javadeveloper.chuckNorrisCipherEncoder;

import java.util.Scanner;

public class Main {

    private static void codingDecoding(ChuckNorrisCipherEncoder coder, InputScanner scanner) {
        Command command = null;
        while (command != Command.EXIT) {
            command = scanner.readCommand();
            switch(command) {
                case DECODE -> {
                    var result = coder.decode(scanner.readInputToDecode());
                    System.out.println(result.isEmpty() ? "Encoded string is not valid.\n" :
                            "Decoded string:\n" + coder.decode(scanner.readInputToDecode()));
                }
                case ENCODE -> {
                    var result = coder.encode(scanner.readInputToEncode());
                    System.out.println(result.isEmpty() ? "String is not valid.\n" :"Encoded string:\n" + result);
                }
                case EXIT -> System.out.println("Bye!");
            }
        }
    }

    public static void main(String[] args) {
        codingDecoding(new ChuckNorrisCipherEncoder(), new InputScanner(new Scanner(System.in)));
    }
}