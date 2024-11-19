package org.farygina.ekaterina.javadeveloper.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputScannerTests {

    @Test
    @DisplayName("Pass 4 inputs, where first 3 are invalid and the last one is correct")
    void testReadCoordinates_WithOutputValidation() {
        String simulatedInput = """
                
                10
                10 20 20
                10 20
                """;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        InputScanner inputScanner = new InputScanner(new Scanner(inputStream));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            //Verify result
            String[] result = inputScanner.readCoordinates();
            assertArrayEquals(new String[]{"10", "20"}, result, "Failed to read valid coordinates");
            // Verify output
            String expectedOutput = """
                    Wrong input! Try again:
                    Wrong input! Try again:
                    Wrong input! Try again:
                    """;
            assertEquals(expectedOutput, outputStream.toString());
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("Pass 6 inputs, where first 5 are invalid and the last one is correct")
    void testReadCoordinate_WithOutputValidation() {
        String simulatedInput = """
                
                A100
                A
                P20
                A20
                A1
                """;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        InputScanner inputScanner = new InputScanner(new Scanner(inputStream));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            //Verify result
            String result = inputScanner.readCoordinate();
            assertEquals("A1", result, "Failed to read valid coordinate");
            // Verify output
            String expectedOutput = """
                    Error! You entered the wrong coordinates! Try again:
                    Error! You entered the wrong coordinates! Try again:
                    Error! You entered the wrong coordinates! Try again:
                    Error! You entered the wrong coordinates! Try again:
                    Error! You entered the wrong coordinates! Try again:
                    """;
            assertEquals(expectedOutput, outputStream.toString());
        } finally {
            System.setOut(originalOut);
        }
    }
}
