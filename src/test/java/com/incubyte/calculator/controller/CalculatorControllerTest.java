package com.incubyte.calculator.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.incubyte.calculator.dto.RequestDTO;
import com.incubyte.calculator.expections.InvalidInputException;
import com.incubyte.calculator.service.CalculatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@WebMvcTest(CalculatorController.class)
public class CalculatorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CalculatorService calculatorService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

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

    @Test
    void test_Add_InvalidString() throws Exception {
        // Given
        RequestDTO requestDTO = new RequestDTO("1 as 2");

        // When
        when(calculatorService.add("1 as 2"))
                .thenThrow(new InvalidInputException("Invalid characters found in the input"));

        // Then
        mockMvc.perform(post("/calculator/add")
               .header("Content-Type", "application/json")
               .content(objectMapper.writeValueAsString(requestDTO)))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.msg", is("Invalid characters found in the input")));
    }

    @Test
    void test_Add_EmptyString() throws Exception {

    }

    @Test
    void test_Add_NullString() throws Exception {

    }
}
