package org.farygina.ekaterina.javadeveloper.chucknorris.encoder;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class InputScannerTest {

    @Test
    void testReadCommand() {
        String simulatedInput = """
                
                smile
                dECOde
                decode
                encode
                exit
                """;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        InputScanner scanner = new InputScanner(new Scanner(inputStream));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            //Verify result
            Command[] result = new Command[]{Command.DECODE, Command.DECODE, Command.ENCODE, Command.EXIT};
            for(Command command : result) {
                assertEquals(command, scanner.readCommand(), String.format("Failed to read %s command", command));
            }
            // Verify output
            String expectedOutput = """
                    Please input operation (encode/decode/exit):
                    There is no '' operation\\n
                    Please input operation (encode/decode/exit):
                    There is no 'smile' operation\\n
                    Please input operation (encode/decode/exit):
                    Please input operation (encode/decode/exit):
                    Please input operation (encode/decode/exit):
                    Please input operation (encode/decode/exit):
                    """;
            assertEquals(expectedOutput, outputStream.toString());
        } finally {
            System.setOut(originalOut);
        }
    }
}