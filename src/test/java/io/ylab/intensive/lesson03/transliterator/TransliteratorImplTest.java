package io.ylab.intensive.lesson03.transliterator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;

@RunWith(Parameterized.class)
public class TransliteratorImplTest {
    private final Transliterator transliterator = new TransliteratorImpl();
    private final String sourceString;
    private final String expected;

    public TransliteratorImplTest(String sourceString, String expected) {
        this.sourceString = sourceString;
        this.expected = expected;
    }

    @Parameterized.Parameters(name = "{index}: Source string: {0} Transliteraed: {1}")
    public static Iterable<Object[]> dataForTest() {
        List<Object[]> dataForTest = new ArrayList<>();
        dataForTest.add(new Object[]{"HELLO! ПРИВЕТ! Go, boy!", "HELLO! PRIVET! Go, boy!"});
        dataForTest.add(new Object[]{"АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ",
                "ABVGDEEZHZIIKLMNOPRSTUFKHTSCHSHSHCHIEY-EIUIA"});
        dataForTest.add(new Object[]{"У ДЕДУШКИ ЗА ПЕЧКОЮ КОМПАНИЯ СИДИТ",
                "U DEDUSHKI ZA PECHKOIU KOMPANIIA SIDIT"});
        return dataForTest;
    }

    @Test
    public void transliterate() {
        Assert.assertEquals(expected, transliterator.transliterate(sourceString));
    }
}