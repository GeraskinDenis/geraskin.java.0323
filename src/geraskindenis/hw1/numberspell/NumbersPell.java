package geraskindenis.hw1.numberspell;

import java.util.Scanner;

public class NumbersPell {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();

            if (n < 0 || n > 30) {
                return;
            }
            System.out.println(numberPell(n));
        }
    }

    public static long numberPell(int n) {

        if (n < 3) {
            return n;
        }

        long[] p = new long[n + 1];
        p[0] = 0;
        p[1] = 1;
        for (int i = 2; i <= n; i++) {
            p[i] = 2 * p[i - 1] + p[i - 2];
        }
        return p[n];
    }
}
