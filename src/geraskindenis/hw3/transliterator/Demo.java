package geraskindenis.hw3.transliterator;

public class Demo {
    public static void main(String[] args) {
        Transliterator transliterator = new TransliteratorImpl();
        System.out.println(transliterator.transliterate("HELLO! ПРИВЕТ! Go, boy!"));
    }
}
