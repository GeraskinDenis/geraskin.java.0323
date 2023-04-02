package io.ylab.intensive.lesson01.guess;

import java.util.Random;
import java.util.Scanner;

public class Guess {
    public static void main(String[] args) {
        int number = new Random().nextInt(99) + 1;
        int maxAttempts = 10;

        System.out.println("Я загадал число. У тебя " + maxAttempts + " попыток угадать.");

        Scanner scanner = new Scanner(System.in);

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            int answer = scanner.nextInt();
            if (answer != number) {
                System.out.printf("Мое чиcло %s! Осталось %d попыток\n",
                        (answer < number) ? "больше" : "меньше", maxAttempts - attempt);
            } else {
                System.out.println("Ты угадал с " + attempt + " попытки!");
                return;
            }
        }
        System.out.println("Ты не угадал");
        scanner.close();
    }
}
