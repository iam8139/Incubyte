package com.incubyte.calculator.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.incubyte.calculator.dto.RequestDTO;
import com.incubyte.calculator.expections.EmptyInputException;
import com.incubyte.calculator.expections.GlobalExceptionHandler;
import com.incubyte.calculator.expections.InvalidInputException;
import com.incubyte.calculator.service.CalculatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@WebMvcTest(CalculatorController.class)
@Import(GlobalExceptionHandler.class)
public class CalculatorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CalculatorService calculatorService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static Stream<Arguments> nullOrEmptyInputsAndMessages() {
        return Stream.of(
                Arguments.of(null, "Input is NULL"),
                Arguments.of("", "Input is Empty")
        );
    }

    private static Stream<Arguments> negativeNumbersAndMessages() {
        return Stream.of(
                Arguments.of("-1, 1", "negative numbers not allowed -1"),
                Arguments.of( "-1, 2, -2", "negative numbers not allowed -1, -2"),
                Arguments.of( "1, -2, -2", "negative numbers not allowed -2"),
                Arguments.of("3, -3, -5, -4", "negative numbers not allowed -3, -4, -5")
        );
    }

    @Test
    void test_HealthCheck() throws Exception {
        mockMvc.perform(get("/calculator/health-check"))
                .andExpect(status().isOk());
    }

    @Test
    void test_Add_ValidString() throws Exception {
        // Given
        RequestDTO requestDTO = new RequestDTO("1, 1");

        // When
        when(calculatorService.add("1, 1")).thenReturn(2);

        // Then
        mockMvc.perform(post("/calculator/add")
                .header("Content-Type", "application/json")
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sum", is(2)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1 as 2", "1.2", "1%2", "//;\n1,2", "//;\n1.2", "//;\n1&2"})
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
    @MethodSource("nullOrEmptyInputsAndMessages")
    void test_Add_NullOrEmptyString(String input, String error) throws Exception {
        // Given
        RequestDTO requestDTO = new RequestDTO(input);

        // Then
        mockMvc.perform(post("/calculator/add")
                        .header("Content-Type", "application/json")
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.msg", is(error)));
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
