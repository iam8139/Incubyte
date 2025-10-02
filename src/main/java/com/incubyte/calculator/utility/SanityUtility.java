package com.incubyte.calculator.utility;

import com.incubyte.calculator.expections.InvalidInputException;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SanityUtility {
    private SanityUtility() {}

    private static final Pattern CUSTOM_DELIMITER = Pattern.compile("^//(.+)\n");
    public static boolean containsCustomDelimiter(String input) {
        return CUSTOM_DELIMITER.matcher(input).find();
    }
    public static String findDelimiter(String input) {
        return containsCustomDelimiter(input) ? findCustomDelimiter(input) : "[,\\n]";
    }
    public static String findNumbersPart(String input) {
        return containsCustomDelimiter(input) ? findLegalString(input) : input;
    }
    public static String findLegalString(String input) {
        Matcher customDelimiterMatcher = CUSTOM_DELIMITER.matcher(input);
        if (!customDelimiterMatcher.find()) {
            throw new InvalidInputException("Input contains invalid character");
        }
        return input.substring(customDelimiterMatcher.end());
    }
    public static String findCustomDelimiter(String input) {
        Matcher customDelimiterMatcher = CUSTOM_DELIMITER.matcher(input);
        if (!customDelimiterMatcher.find()) {
            throw new InvalidInputException("Input contains invalid character");
        }

        return customDelimiterMatcher.group(1);
    }
    public static List<Integer> findNegativeNumbers(String input, String delimiter) {
        return Arrays.stream(input.split(delimiter))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .filter(i -> i < 0)
                .toList();
    }
}
