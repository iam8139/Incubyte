package com.incubyte.calculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.incubyte.calculator.dto.RequestDTO;
import com.incubyte.calculator.expections.GlobalExceptionHandler;
import com.incubyte.calculator.service.CalculatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CalculatorController.class)
@Import(GlobalExceptionHandler.class)
public class CalculatorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CalculatorService calculatorService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static Stream<Arguments> negativeNumbersAndMessages() {
        return Stream.of(
                Arguments.of("-1, 1", "negative numbers not allowed -1"),
                Arguments.of( "-1, 2, -2", "negative numbers not allowed -1, -2"),
                Arguments.of( "1, -2, -2", "negative numbers not allowed -2"),
                Arguments.of("3, -3, -5, -4", "negative numbers not allowed -3, -4, -5"),
                Arguments.of( "//;\n-1; 2; -2", "negative numbers not allowed -1, -2"),
                Arguments.of( "//-\n1- 2- -2", "Invalid delimiter -"),
                Arguments.of( "//-\n1- 2-\n -2", "Invalid delimiter -")
        );
    }

    private static Stream<Arguments> validInput() {
        return Stream.of(
                Arguments.of("", 0),
                Arguments.of("1, 2", 3),
                Arguments.of("1, 2, 4,7, 9", 23),
                Arguments.of("//;\n1; 2; 3", 6),
                Arguments.of("//;\n1\n2; 3", 6),
                Arguments.of("1\n2, 3", 6),
                Arguments.of("//;\n1;2\n3;4", 10)
        );
    }

    @Test
    void test_HealthCheck() throws Exception {
        mockMvc.perform(get("/calculator/health-check"))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @MethodSource("validInput")
    void test_Add_ValidString(String input, Integer sum) throws Exception {
        // Given
        RequestDTO requestDTO = new RequestDTO(input);

        // When
        when(calculatorService.add(input)).thenReturn(sum);

        // Then
        mockMvc.perform(post("/calculator/add")
                .header("Content-Type", "application/json")
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sum", is(sum)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1 as 2", "1.2", "1%2", "//;\n1,2", "//;\n1.2", "//;\n1&2", "//;\n1\n,2"})
    void test_Add_InvalidString(String input) throws Exception {
        // Given
        RequestDTO requestDTO = new RequestDTO(input);

        // Then
        mockMvc.perform(post("/calculator/add")
               .header("Content-Type", "application/json")
               .content(objectMapper.writeValueAsString(requestDTO)))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.msg", is("Input contains invalid character")));
    }

    @ParameterizedTest
    @MethodSource("negativeNumbersAndMessages")
    void test_Add_NegativeNumbers(String input, String error) throws Exception {
        // Given
        RequestDTO requestDTO = new RequestDTO(input);

        // Then
        mockMvc.perform(post("/calculator/add")
                        .header("Content-Type", "application/json")
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.msg", is(error)));
    }
}
