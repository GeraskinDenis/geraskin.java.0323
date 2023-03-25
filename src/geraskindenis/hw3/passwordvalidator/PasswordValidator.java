package geraskindenis.hw3.passwordvalidator;

import geraskindenis.hw3.passwordvalidator.exceptions.WrongLoginException;
import geraskindenis.hw3.passwordvalidator.exceptions.WrongPasswordException;

import java.util.Arrays;

public class PasswordValidator {
    private static final char[] ALLOWED_CHARACTERS;

    static {
        ALLOWED_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz".toCharArray();
        Arrays.sort(ALLOWED_CHARACTERS);
    }

    public static boolean validate(String login, String password, String confirmPassword) {
        try {
            validateLogin(login);
            validatePassword(password, confirmPassword);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static void validateLogin(String login) throws WrongLoginException {
        if (login.length() >= 20) {
            throw new WrongLoginException("Логин слишком длинный");
        }

        for (char c : login.toCharArray()) {
            if (Arrays.binarySearch(ALLOWED_CHARACTERS, c) < 0) {
                throw new WrongLoginException("Логин содержит недопустимые символы");
            }
        }
    }

    private static void validatePassword(String password, String confirmPassword)
            throws WrongPasswordException {

        if (password.length() >= 20) {
            throw new WrongPasswordException("Пароль слишком длинный");
        }

        for (char c : password.toCharArray()) {
            if (Arrays.binarySearch(ALLOWED_CHARACTERS, c) < 0) {
                throw new WrongPasswordException("Пароль содержит недопустимые символы");
            }
        }

        if (!password.equals(confirmPassword)) {
            throw new WrongPasswordException("Пароль и подтверждение не совпадают");
        }
    }
}
