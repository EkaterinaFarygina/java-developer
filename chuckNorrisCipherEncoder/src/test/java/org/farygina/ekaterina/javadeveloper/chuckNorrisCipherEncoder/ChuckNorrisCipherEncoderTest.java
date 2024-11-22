package org.farygina.ekaterina.javadeveloper.chuckNorrisCipherEncoder;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class ChuckNorrisCipherEncoderTest {

    static ChuckNorrisCipherEncoder coder;

    @BeforeAll
    static void createCoder() {
        coder = new ChuckNorrisCipherEncoder();
    }

    @ParameterizedTest
    @CsvSource(value = {"null, '', Passing null value",
            "'', '', Passing empty string",
            "C, 0 0 00 0000 0 00, Passing one letter text",
            "CC, 0 0 00 0000 0 000 00 0000 0 00, Passing several letters text",
            "Hi <3, 0 0 00 00 0 0 00 000 0 00 00 0 0 0 00 00 0 0 00 0 0 0 00 000000 0 0000 00 000 0 00 00 00 0 00, " +
                    "Passing text with letters and symbols"},
            nullValues = {"null"})
    void testEncode(String text, String expected, String message) {
        assertEquals(expected, coder.encode(text), message);
    }

    @ParameterizedTest
    @CsvSource(value = {"null, '', Passing null value",
            "'', '', Passing empty string",
            "0 0 00 00 0 0 00 000, H, Passing one letter code",
            "0 0 00 0000 0 000 00 0000 0 00, CC, Passing several letters code",
            "0 0 1 00 0 0 1 000, '', Passing code with symbols other zero and space",
            "000 0 00 00 0000 0 00 000, '', Passing code with starting code blocks other than 0 or 00",
            "0 0 00 00 0 0 00, '', Passing not even number of blocks",
            "0 0 00 00 0 0 00 00, '', Passing not 7 bit letters"},
            nullValues = {"null"})
    void testDecode(String code, String expected, String message) {
        assertEquals(expected, coder.decode(code), message);
    }
}