package com.incubyte.calculator.utility;

import com.incubyte.calculator.expections.InvalidInputException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SanityUtility {
    private SanityUtility() {}

    private static final Pattern CUSTOM_DELIMITER = Pattern.compile("^//(.+)\n");
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
}
