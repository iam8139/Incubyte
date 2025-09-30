package com.incubyte.calculator.expections;

import com.incubyte.calculator.dto.ValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ValidationError> handleInvalidInputException(InvalidInputException ex) {
        return new ResponseEntity<>(new ValidationError(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ValidationError> handleNullPointerException(InvalidInputException ex) {
        return new ResponseEntity<>(new ValidationError(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
