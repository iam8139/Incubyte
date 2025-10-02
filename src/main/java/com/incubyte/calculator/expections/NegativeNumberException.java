package com.incubyte.calculator.expections;

public class NegativeNumberException extends RuntimeException {
    public NegativeNumberException(String msg) {
        super(msg);
    }
    public NegativeNumberException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
