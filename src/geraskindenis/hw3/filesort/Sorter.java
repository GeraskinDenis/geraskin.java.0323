package geraskindenis.hw3.filesort;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Sorter {
    private final int MAX_CACHE_IN_BYTE;
    private final String LINE_SEPARATOR = System.lineSeparator();
    private String strTmpFolder;
    private int fileCounter;

    public Sorter() {
        this.MAX_CACHE_IN_BYTE = 1024 * 1024; //1Mb
    }

    public Sorter(int MAX_CACHE_IN_BYTE) {
        this.MAX_CACHE_IN_BYTE = MAX_CACHE_IN_BYTE;
    }

    public File sortFile(File dataFile) throws IOException {

        if (!dataFile.exists()) {
            throw new IOException(String.format("File %s is not exist!", dataFile.getAbsolutePath()));
        }

        if (dataFile.isDirectory()) {
            throw new IOException(String.format("File %s is folder!", dataFile.getAbsolutePath()));
        }

        fileCounter = 0;
        strTmpFolder = dataFile.getParent() + "\\tmp";
        File tmpFolder = new File(strTmpFolder);
        tmpFolder.mkdirs();
        List<File> listOfSortedFiles = splitIntoSortedFiles(dataFile);
        List<File> sortedFiles;
        if (listOfSortedFiles.size() == 1) {
            sortedFiles = listOfSortedFiles;
        } else {
            sortedFiles = mergingSortedFiles(listOfSortedFiles);
        }
        File result = sortedFiles.get(0);
        File sortedFile = new File(dataFile.getParent() + "\\sorted_" + dataFile.getName());
        result.renameTo(sortedFile);
        tmpFolder.delete();
        return sortedFile;
    }

    private List<File> mergingSortedFiles(List<File> listOfSortedFiles) throws IOException {
        List<File> result = new ArrayList<>();

        for (int i = 0; i < listOfSortedFiles.size(); ) {
            File f1 = listOfSortedFiles.get(i++);
            File f2 = null;
            if (i < listOfSortedFiles.size()) {
                f2 = listOfSortedFiles.get(i++);
            }
            result.add(mergingTwoFiles(f1, f2));
            f1.delete();
            if (Objects.nonNull(f2)) {
                f2.delete();
            }
        }

        if (result.size() > 1) {
            result = mergingSortedFiles(result);
        }

        return result;
    }

    private File mergingTwoFiles(File f1, File f2) throws IOException {

        if (Objects.isNull(f2)) {
            return f1;
        }

        File dstFile = new File(strTmpFolder + "\\tmp" + fileCounter++ + ".tmp");
        FileWriter writer = new FileWriter(dstFile);

        Scanner s1 = new Scanner(f1);
        Scanner s2 = new Scanner(f2);

        long l1 = Long.MIN_VALUE;
        long l2 = Long.MIN_VALUE;

        boolean l1IsWritten = true;
        boolean l2IsWritten = true;

        while (true) {
            if (s1.hasNextLong() && l1IsWritten) {
                l1 = s1.nextLong();
                l1IsWritten = false;
            }

            if (s2.hasNextLong() && l2IsWritten) {
                l2 = s2.nextLong();
                l2IsWritten = false;
            }

            if (!(l1IsWritten || l2IsWritten)) {
                if (l1 > l2) {
                    writer.write(l2 + LINE_SEPARATOR);
                    l2IsWritten = true;
                } else if (l1 < l2) {
                    writer.write(l1 + LINE_SEPARATOR);
                    l1IsWritten = true;
                } else {
                    writer.write(l1 + LINE_SEPARATOR);
                    writer.write(l2 + LINE_SEPARATOR);
                    l1IsWritten = true;
                    l2IsWritten = true;
                }
            } else if (!l1IsWritten) {
                writer.write(l1 + LINE_SEPARATOR);
                l1IsWritten = true;
            } else if (!l2IsWritten) {
                writer.write(l2 + LINE_SEPARATOR);
                l2IsWritten = true;
            } else {
                break;
            }
        }

        s1.close();
        s2.close();
        writer.flush();
        writer.close();
        return dstFile;
    }

    private List<File> splitIntoSortedFiles(File dataFile) {
        List<File> listOfSortedFiles = new ArrayList<>();

        try (Scanner scanner = new Scanner(dataFile)) {

            long[] cache = new long[MAX_CACHE_IN_BYTE / (Long.SIZE / 8)];
            int indexOfCache = 0;

            while (scanner.hasNextLong()) {

                if (indexOfCache == cache.length) {
                    Arrays.sort(cache);
                    File dstFile = new File(strTmpFolder + "\\tmp" + fileCounter++ + ".tmp");
                    saveToFile(cache, dstFile);
                    listOfSortedFiles.add(dstFile);
                    cache = new long[cache.length];
                    indexOfCache = 0;
                }
                cache[indexOfCache++] = scanner.nextLong();
            }

            cache = Arrays.copyOf(cache, indexOfCache);
            Arrays.sort(cache);
            File dstFile = new File(strTmpFolder + "\\tmp" + fileCounter++ + ".tmp");
            saveToFile(cache, dstFile);
            listOfSortedFiles.add(dstFile);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return listOfSortedFiles;
    }

    private void saveToFile(long[] cache, File dstFile) throws IOException {
        FileWriter writer = new FileWriter(dstFile);
        for (long l : cache) {
            writer.write(l + LINE_SEPARATOR);
        }
        writer.flush();
        writer.close();
    }


}
