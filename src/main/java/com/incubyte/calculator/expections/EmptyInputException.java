package com.incubyte.calculator.expections;

public class EmptyInputException extends RuntimeException {
    public EmptyInputException(String msg) {
        super(msg);
    }
    public EmptyInputException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
