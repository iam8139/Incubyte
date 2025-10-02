package com.incubyte.calculator.validators;

import com.incubyte.calculator.expections.EmptyInputException;
import com.incubyte.calculator.expections.InvalidInputException;
import com.incubyte.calculator.utility.SanityUtility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorValidators {
    private static final Pattern INVALID_CHAR = Pattern.compile("^[0-9,\\s\\-]*$");
    private static final Pattern CUSTOM_DELIMITER = Pattern.compile("^//(.+)\n");
    private CalculatorValidators() {
        throw new IllegalArgumentException("Object Creation not allowed");
    }

    private static void isNull(String input) {
        if (input == null) throw new NullPointerException("Input is NULL");
    }

    private static void isEmpty(String input) {
        if (input.isBlank()) throw new EmptyInputException("Input is Empty");
    }

    private static boolean containsCustomDelimiter(String input) {
        return CUSTOM_DELIMITER.matcher(input).find();
    }
    public static void isNullOrEmpty(String input) {
        isNull(input);
        isEmpty(input);
    }
    public static void validateInvalidCharacter(String input) {
        if (!containsCustomDelimiter(input)) {
            if (!INVALID_CHAR.matcher(input).matches()) throw new InvalidInputException("Input contains invalid character");
        } else {
            String customDelimiter = SanityUtility.findCustomDelimiter(input);
            String numbersPart = SanityUtility.findLegalString(input);
            String numbersRegexString = "^[0-9\\s-" + Pattern.quote(customDelimiter) + "]*$";
            Pattern numbersPattern = Pattern.compile(numbersRegexString);

            if (!numbersPattern.matcher(numbersPart).matches()) {
                throw new InvalidInputException("Input contains invalid character");
            }
        }
    }
}
