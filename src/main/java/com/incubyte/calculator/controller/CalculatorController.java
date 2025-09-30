package com.incubyte.calculator.controller;

import com.incubyte.calculator.dto.RequestDTO;
import com.incubyte.calculator.dto.ResponseDTO;
import com.incubyte.calculator.service.CalculatorService;
import jdk.jfr.ContentType;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calculator")
@AllArgsConstructor
public class CalculatorController {

    private final CalculatorService calculatorService;

    @GetMapping("/health-check")
    public ResponseEntity<?> healthCheck() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> add(@RequestBody RequestDTO requestDTO) {
        int result = calculatorService.add(requestDTO.getInput());
        return new ResponseEntity<>(new ResponseDTO(result), HttpStatus.OK);
    }
}
