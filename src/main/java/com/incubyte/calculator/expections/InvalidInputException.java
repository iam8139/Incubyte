package com.incubyte.calculator.expections;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String msg) {
        super(msg);
    }

    public InvalidInputException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
