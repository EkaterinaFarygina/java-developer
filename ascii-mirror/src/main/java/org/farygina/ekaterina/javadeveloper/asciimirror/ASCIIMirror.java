package org.farygina.ekaterina.javadeveloper.asciimirror;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;

public class ASCIIMirror {
    private final ArrayList<String> text;
    private final HashMap<Character, Character> horizontalOppositeChars;

    ASCIIMirror(String filepath) throws IOException {
        this.text = formatText(readFileContent(filepath));
        this.horizontalOppositeChars = createSetHorizontalOppositeChars();
    }

    private HashMap<Character, Character> createSetHorizontalOppositeChars() {
        HashMap<Character, Character> horizontalOppositeChars = new HashMap<>();
        horizontalOppositeChars.put('<', '>');
        horizontalOppositeChars.put('>', '<');
        horizontalOppositeChars.put('[', ']');
        horizontalOppositeChars.put(']', '[');
        horizontalOppositeChars.put('{', '}');
        horizontalOppositeChars.put('}', '{');
        horizontalOppositeChars.put('(', ')');
        horizontalOppositeChars.put(')', '(');
        horizontalOppositeChars.put('/', '\\');
        horizontalOppositeChars.put('\\', '/');
        return horizontalOppositeChars;
    }

    private ArrayList<String> readFileContent(String filePath) throws IOException {
        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
        ArrayList<String> result = reader.lines().collect(Collectors.toCollection(ArrayList::new));
        reader.close();
        return result;
    }

    private ArrayList<String> formatText(ArrayList<String> text) {
        int maxLength = text.stream()
                .max(Comparator.comparingInt(String::length))
                .orElse("")
                .length();
        String format = "%-" + maxLength + "s";
        text.replaceAll(element -> element.length() < maxLength ? String.format(format, element) : element);
        return text;
    }

    private String reverseText(String text) {
        StringBuilder builder = new StringBuilder();
        for (int i = text.length() - 1; i >= 0; i--) {
            char ch = text.charAt(i);
            if (this.horizontalOppositeChars.containsKey(ch)) {
                builder.append(this.horizontalOppositeChars.get(ch));
            } else {
                builder.append(ch);
            }
        }
        return builder.toString();
    }

    public void printMirror() {
        this.text.forEach(element ->
                System.out.println(element + " | " + reverseText(element))
        );
    }

}
