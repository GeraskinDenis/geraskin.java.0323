package io.ylab.intensive.lesson02.sequences;

import java.util.stream.IntStream;

public class SequenceGeneratorImpl implements SequenceGenerator {
    @Override
    public void a(int n) {
        IntStream.range(1, n + 1)
                .map(i -> i * 2)
                .forEach(System.out::println);
    }

    @Override
    public void b(int n) {
        IntStream.range(1, n + 1)
                .map(i -> i + i - 1)
                .forEach(System.out::println);
    }

    @Override
    public void c(int n) {
        IntStream.range(1, n + 1)
                .map(i -> i * i)
                .forEach(System.out::println);
    }

    @Override
    public void d(int n) {
        IntStream.range(1, n + 1)
                .map(i -> (int) Math.pow(i, 3))
                .forEach(System.out::println);
    }

    @Override
    public void e(int n) {
        IntStream.range(1, n + 1)
                .map(i -> (int) Math.pow(-1, i - 1))
                .forEach(System.out::println);
    }

    @Override
    public void f(int n) {
        IntStream.range(1, n + 1)
                .map(i -> ((int) Math.pow(-1, i - 1)) * i)
                .forEach(System.out::println);
    }

    @Override
    public void g(int n) {
        IntStream.range(1, n + 1)
                .map(i -> ((int) Math.pow(-1, i - 1)) * i * i)
                .forEach(System.out::println);
    }

    @Override
    public void h(int n) {
        IntStream.range(1, n + 1)
                .map(i -> (i % 2) * (i - i / 2))
                .forEach(System.out::println);
    }

    @Override
    public void i(int n) {
        IntStream.range(1, n + 1)
                .map(i -> {
                    int result = 1;
                    for (int j = 2; j < i + 1; j++) {
                        result += result * (j - 1);
                    }
                    return result;
                })
                .forEach(System.out::println);
    }

    @Override
    public void j(int n) {
        IntStream.range(1, n + 1)
                .map(i -> {
                    int a = 0;
                    int b = 1;
                    for (int g = 2; g < i + 1; g++) {
                        int next = a + b;
                        a = b;
                        b = next;
                    }
                    return b;
                })
                .forEach(System.out::println);
    }
}
