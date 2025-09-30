package com.incubyte.calculator.validators;

public class CalculatorValidators {
    private CalculatorValidators() {

    }

    public static void isNull(String input) {
        if (input == null) throw new NullPointerException("Input is NULL");
    }

    public static void isEmpty(String input) {
        if (input.isBlank()) throw new NullPointerException("Input is Empty");
    }
}
