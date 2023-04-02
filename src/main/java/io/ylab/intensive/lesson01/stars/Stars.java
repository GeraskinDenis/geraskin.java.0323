package io.ylab.intensive.lesson01.stars;

import java.util.Scanner;

public class Stars {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int lines = scanner.nextInt();
            int columns = scanner.nextInt();
            String template = scanner.next();

            if (columns == 0) {
                return;
            }

            for (int line = 0; line < lines; line++) {
                System.out.println((template + " ").repeat(columns - 1) + template);
            }

        }
    }
}
