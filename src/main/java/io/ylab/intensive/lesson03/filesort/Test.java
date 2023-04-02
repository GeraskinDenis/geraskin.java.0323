package io.ylab.intensive.lesson03.filesort;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class Test {
    public static void main(String[] args) throws IOException {
        System.out.println(new Date());
        File dataFile = new Generator().generate("src/geraskindenis/hw3/filesort/data.txt", 375_000_000);
        System.out.println(new Validator(dataFile).isSorted()); // false
        File sortedFile = new Sorter().sortFile(dataFile);
        System.out.println(new Validator(sortedFile).isSorted()); // true
        System.out.println(new Date());
    }
}
