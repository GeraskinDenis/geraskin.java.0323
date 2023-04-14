package io.ylab.intensive.lesson03.transliterator;

public class Demo {
    public static void main(String[] args) {
        Transliterator transliterator = new TransliteratorImpl();
        System.out.println(transliterator.transliterate("HELLO! ПРИВЕТ! Go, boy!"));
    }
}
