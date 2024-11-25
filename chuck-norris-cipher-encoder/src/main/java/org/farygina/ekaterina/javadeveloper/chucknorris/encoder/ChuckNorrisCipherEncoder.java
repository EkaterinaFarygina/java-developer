package org.farygina.ekaterina.javadeveloper.chucknorris.encoder;

public class ChuckNorrisCipherEncoder {

    private static final char ZERO = '0';
    private static final char ONE = '1';
    private static final char SEPARATOR = ' ';

    private String convertTextToBinary(String text) {
        var builder = new StringBuilder();
        text.chars()
                .forEach(ch -> builder.append(String.format("%7s", Integer.toBinaryString(ch)).replace(SEPARATOR, ZERO)));
        return builder.toString();
    }

    public String encode(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        var builder = new StringBuilder();
        var binary = convertTextToBinary(text);
        int i = 0;
        while (i < binary.length()) {
            var current = binary.charAt(i) == ZERO ? ZERO : ONE;
            builder.append(current == ZERO ? "00 " : "0 ");
            while (i != binary.length() && binary.charAt(i) == current) {
                builder.append(ZERO);
                i++;
            }
            builder.append(SEPARATOR);
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    private String convertCodeToBinary(String code) {
        var builder = new StringBuilder();
        var parts = code.split(" ");
        if (parts.length % 2 == 1) {
            return "";
        }
        for (int i = 0; i < parts.length; i += 2) {
            String part1 = parts[i];
            String part2 = parts[i + 1];
            final char toAdd;
            switch (part1) {
                case "00" -> toAdd = ZERO;
                case "0" -> toAdd = ONE;
                default -> {
                    return "";
                }
            }
            if (!part2.matches("0+")) {
                return "";
            }
            builder.append(String.valueOf(toAdd).repeat(part2.length()));
        }
        return builder.toString();
    }

    public String decode(String code) {
        if (code == null || code.isEmpty()) {
            return "";
        }
        var binary = convertCodeToBinary(code);
        if (binary.isEmpty() || binary.length() % 7 != 0) {
            return "";
        }
        var builder = new StringBuilder();
        for (int i = 0; i < binary.length(); i += 7) {
            builder.append((char) Integer.parseInt(binary.substring(i, i + 7), 2));
        }
        return builder.toString();
    }
}
