package io.ylab.intensive.lesson03.passwordvalidator.exceptions;

public class WrongLoginException extends Exception {
    public WrongLoginException() {
    }

    public WrongLoginException(String message) {
        super(message);
    }
}
