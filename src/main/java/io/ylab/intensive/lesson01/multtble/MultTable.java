package io.ylab.intensive.lesson01.multtble;

public class MultTable {
    public static void main(String[] args) {
        for (int m1 = 1; m1 < 10; m1++) {
            for (int m2 = 1; m2 < 10; m2++) {
                System.out.printf("%d + %d = %d%n", m1, m2, m1 * m2);
            }
        }
    }
}
