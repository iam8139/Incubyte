package com.incubyte.calculator.validators;

import com.incubyte.calculator.expections.EmptyInputException;
import com.incubyte.calculator.expections.InvalidInputException;
import com.incubyte.calculator.expections.NegativeNumberException;
import com.incubyte.calculator.utility.SanityUtility;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.incubyte.calculator.utility.SanityUtility.*;

public class CalculatorValidators {
    private static final Pattern INVALID_CHAR = Pattern.compile("[^0-9\\s-,]+");
    private static final Pattern NUMBER_VALIDATOR = Pattern.compile("^-?\\d+$");
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
    public static void validateInvalidCharacter(String input) {
        String customDelimiter = "[,\\n]";
        String numbersPart = input;
        if (containsCustomDelimiter(input)) {
            customDelimiter = SanityUtility.findCustomDelimiter(input);
            numbersPart = SanityUtility.findLegalString(input);
            String numbersRegexString = "^[0-9\\s-" + Pattern.quote(customDelimiter) + "\\n]+";
            Pattern numbersPattern = Pattern.compile(numbersRegexString);

            if (!numbersPattern.matcher(numbersPart).matches()) {
                throw new InvalidInputException("Input contains invalid character");
            }
            customDelimiter = Pattern.compile(customDelimiter) + "|\\n";
        } else {
            if (INVALID_CHAR.matcher(input).find())
                throw new InvalidInputException("Input contains invalid character");
        }

        String[] numberTokens = numbersPart.split(customDelimiter);

        for (String token : numberTokens) {
            String trimmedToken = token.trim();
            if (!trimmedToken.isEmpty() && !NUMBER_VALIDATOR.matcher(trimmedToken).matches()) {
                throw new InvalidInputException("Input contains a malformed number: " + trimmedToken);
            }
        }
    }

    public static void validateNegativeNumbers(String input) {
        if (input.contains("-")) {
            String delimiter = findDelimiterForNegativeCheck(input);
            String numbersPart = findNumbersPart(input);

            if (delimiter.equals("-")) throw new InvalidInputException("Invalid delimiter -");

            List<Integer> negatives = SanityUtility.findNegativeNumbers(numbersPart, delimiter);
            Set<String> negativeString = negatives.stream().map(String::valueOf).collect(Collectors.toSet());

            throw new NegativeNumberException("negative numbers not allowed " + String.join(", ", negativeString));
        }
    }
}
