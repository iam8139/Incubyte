package com.incubyte.calculator.validators;

import com.incubyte.calculator.expections.EmptyInputException;
import com.incubyte.calculator.expections.InvalidInputException;

import java.util.regex.Pattern;

public class CalculatorValidators {
    private static final String INVALID_CHAR_REGEX = "^[0-9,\\s\\-]*$";
    private static final Pattern INVALID_CHAR = Pattern.compile(INVALID_CHAR_REGEX);
    private CalculatorValidators() {
        throw new IllegalArgumentException("Object Creation not allowed");
    }

    private static void isNull(String input) {
        if (input == null) throw new NullPointerException("Input is NULL");
    }

    private static void isEmpty(String input) {
        if (input.isBlank()) throw new EmptyInputException("Input is Empty");
    }

    public static void isNullOrEmpty(String input) {
        isNull(input);
        isEmpty(input);
    }

    public static void containsInvalidCharacter(String input) {
        if (!INVALID_CHAR.matcher(input).matches()) throw new InvalidInputException("Input contains invalid character");
    }
}
