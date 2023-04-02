package io.ylab.intensive.lesson04.filesort;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Validator {
    private final File file;

    public Validator(File file) {
        this.file = file;
    }

    public boolean isSortedAscending() {
        try (Scanner scanner = new Scanner(new FileInputStream(file))) {
            long prev = Long.MIN_VALUE;
            while (scanner.hasNextLong()) {
                long current = scanner.nextLong();
                if (current < prev) {
                    return false;
                } else {
                    prev = current;
                }
            }
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean isSortedDescending() {
        try (Scanner scanner = new Scanner(new FileInputStream(file))) {
            long prev = Long.MAX_VALUE;
            while (scanner.hasNextLong()) {
                long current = scanner.nextLong();
                if (current > prev) {
                    return false;
                } else {
                    prev = current;
                }
            }
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
