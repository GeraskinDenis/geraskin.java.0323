package io.ylab.intensive.lesson03.transliterator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TransliteratorImpl implements Transliterator {
    private static final Map<String, String> RULES_OF_TRANSLITERATE = new HashMap<>();

    static {
        String[] initialRules = {"А, A", "Б, B", "В, V", "Г, G", "Д, D", "Е, E", "Ё, E", "Ж, ZH",
                "З, Z", "И, I", "Й, I", "К, K", "Л, L", "М, M", "Н, N", "О, O", "П, P", "Р, R",
                "С, S", "Т, T", "У, U", "Ф, F", "Х, KH", "Ц, TS", "Ч, CH", "Ш, SH", "Щ, SHCH",
                "Ы, Y", "Ь, -", "Ъ, IE", "Э, E", "Ю, IU", "Я, IA"};
        Arrays.stream(initialRules).forEach(s -> {
            String[] strArr = s.split(",");
            RULES_OF_TRANSLITERATE.put(strArr[0].trim(), strArr[1].trim());
        });
    }

    @Override
    public String transliterate(String source) {
        StringBuilder builder = new StringBuilder();
        Arrays.stream(source.split("")).forEach(s -> builder.append(transliterateSymbol(s)));
        return builder.toString();
    }

    private String transliterateSymbol(String s) {
        String result = RULES_OF_TRANSLITERATE.get(s);
        return (Objects.nonNull(result)) ? result : s;
    }
}
