package io.ylab.intensive.lesson02.snilsvalidator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;

@RunWith(Parameterized.class)
public class SnilsValidatorImplTest {
    private final SnilsValidatorImpl snilsValidator = new SnilsValidatorImpl();
    private final String snils;
    private final boolean expected;
    private final String message;

    public SnilsValidatorImplTest(String snils, boolean expected, String message) {
        this.snils = snils;
        this.expected = expected;
        this.message = message;
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> dataForTest() {
        List<Object[]> dataForTest = new ArrayList<>();
        dataForTest.add(new Object[]{"01468870570", false, "Invalid snils number."});
        dataForTest.add(new Object[]{"9004441", false, "The snils number is too short."});
        dataForTest.add(new Object[]{"90114F04441", false, "The snils number has an invalid character."});
        dataForTest.add(new Object[]{"90114404441", true, "The valid snils number."});
        return dataForTest;
    }

    @Test
    public void validate() {
        System.out.println(message);
        Assert.assertEquals(expected, snilsValidator.validate(snils));
    }
}