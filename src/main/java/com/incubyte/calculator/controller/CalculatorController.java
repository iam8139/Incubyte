package com.incubyte.calculator.controller;

import com.incubyte.calculator.service.CalculatorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculator")
@AllArgsConstructor
public class CalculatorController {

    private final CalculatorService calculatorService;

    @GetMapping("/health-check")
    public ResponseEntity<?> healthCheck() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Integer> add(String numbers) {
        int result = calculatorService.add(numbers);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
