package io.ylab.intensive.lesson02.snilsvalidator;

public class SnilsValidatorImpl implements SnilsValidator {
    private final int LENGTH_OF_SNILS = 11;

    @Override
    public boolean validate(String snils) {
        if (snils == null || snils.length() != LENGTH_OF_SNILS) {
            return false;
        }

        int[] snilsNumbers = new int[LENGTH_OF_SNILS];
        try {
            char[] chars = snils.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                snilsNumbers[i] = Integer.parseInt(String.valueOf(chars[i]));
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return checkSum(snilsNumbers);
    }

    private boolean checkSum(int[] snilsNumbers) {
        int[] c = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        int sum = 0;
        for (int i = 0; i < c.length; i++) {
            sum += c[i] * snilsNumbers[i];
        }
        int s = Integer.parseInt(String.valueOf(snilsNumbers[9]) + snilsNumbers[10]);
        return (sum % 101 % 100) == s;
    }
}
