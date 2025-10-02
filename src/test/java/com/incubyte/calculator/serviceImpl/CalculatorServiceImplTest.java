package com.incubyte.calculator.serviceImpl;

import com.incubyte.calculator.service.CalculatorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CalculatorServiceImplTest {
    @InjectMocks
    private CalculatorServiceImpl calculatorService;
    private static Stream<Arguments> generateInputAndSum() {
        return Stream.of(
                Arguments.of("1, 2", 3),
                Arguments.of("1\n2,3", 6),
                Arguments.of("//;\n1;2", 3),
                Arguments.of("1", 1),
                Arguments.of("//;\n1;2\n3;4", 10)
        );
    }
    @ParameterizedTest
    @MethodSource("generateInputAndSum")
    void test_Add(String input, Integer sum) {
        Integer res = calculatorService.add(input);

        // Then
        assertEquals(res, sum);
    }
}
