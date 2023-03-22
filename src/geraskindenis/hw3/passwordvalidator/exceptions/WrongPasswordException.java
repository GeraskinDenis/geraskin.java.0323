package geraskindenis.hw3.passwordvalidator.exceptions;

public class WrongPasswordException extends Exception {
    public WrongPasswordException() {
    }

    public WrongPasswordException(String message) {
        super(message);
    }
}
