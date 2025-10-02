package com.incubyte.calculator.serviceImpl;

import com.incubyte.calculator.expections.InvalidInputException;
import com.incubyte.calculator.service.CalculatorService;
import com.incubyte.calculator.utility.SanityUtility;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

import static com.incubyte.calculator.utility.SanityUtility.findDelimiter;
import static com.incubyte.calculator.utility.SanityUtility.findNumbersPart;

@Service
public class CalculatorServiceImpl implements CalculatorService {
    @Override
    public int add(String input) {
        return findSum(findNumbersPart(input), findDelimiter(input)).orElseThrow();
    }

    private Optional<Integer> findSum(String input, String delimiter) {
        return Arrays.stream(input.split(delimiter))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt).reduce(Integer::sum);
    }
}
