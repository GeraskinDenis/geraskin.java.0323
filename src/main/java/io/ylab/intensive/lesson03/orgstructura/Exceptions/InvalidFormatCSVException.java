package io.ylab.intensive.lesson03.orgstructura.Exceptions;

public class InvalidFormatCSVException extends RuntimeException {
    public InvalidFormatCSVException(String message) {
        super(message);
    }
}
