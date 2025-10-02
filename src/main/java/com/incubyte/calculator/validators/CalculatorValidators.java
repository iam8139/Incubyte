package com.incubyte.calculator.validators;

import com.incubyte.calculator.expections.EmptyInputException;

public class CalculatorValidators {
    private CalculatorValidators() {

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
}
