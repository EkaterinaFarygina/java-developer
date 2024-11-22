package org.farygina.ekaterina.javadeveloper.chuckNorrisCipherEncoder;

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
        while (!binary.isEmpty()) {
            var current = binary.charAt(0) == ZERO ? ZERO : ONE;
            builder.append(current == ZERO ? "00 " : "0 ");
            while (!binary.isEmpty() && binary.charAt(0) == current) {
                builder.append(ZERO);
                binary = binary.substring(1);
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
        var toAdd = ZERO;
        for (int i = 0; i < parts.length; i++) {
            if(i % 2 == 0) {
                switch(parts[i]) {
                    case "00" -> toAdd = ZERO;
                    case "0" -> toAdd = ONE;
                    default -> {
                        return "";
                    }
                }
            } else {
                if(!parts[i].matches("0+")) {
                    return "";
                }
                builder.append(String.valueOf(toAdd).repeat(parts[i].length()));
            }
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
        while(!binary.isEmpty()) {
            builder.append((char) Integer.parseInt(binary.substring(0, 7), 2));
            binary = binary.substring(7);
        }
        return builder.toString();
    }
}
