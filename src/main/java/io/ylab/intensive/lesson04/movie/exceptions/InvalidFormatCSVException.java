package io.ylab.intensive.lesson04.movie.exceptions;

public class InvalidFormatCSVException extends RuntimeException {
    public InvalidFormatCSVException(String message) {
        super(message);
    }
}
