package io.ylab.intensive.lesson04.filesort;

import javax.sql.DataSource;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FileSortImpl implements FileSorter {
    protected final DataSource dataSource;
    private final int BATCH_SIZE = 100;
    private final String LINE_SEPARATOR = System.lineSeparator();

    public FileSortImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public File sort(File data) {
        List<Long> longList = readFile(data);
        saveToDatabase(longList);
        File sortedFile = getSortedFile(data.getParent() + "\\sorted_" + data.getName());
        return sortedFile;
    }

    private List<Long> readFile(File srcFile) {
        List<Long> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(srcFile))) {
            while (reader.ready()) {
                result.add(Long.parseLong(reader.readLine()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private File getSortedFile(String path) {
        String sql = "SELECT val FROM numbers ORDER BY val DESC";
        File sortedFile = new File(path);
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql);
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(sortedFile))) {
            while (resultSet.next()) {
                bufferedWriter.write(resultSet.getLong(1) + LINE_SEPARATOR);
            }
            bufferedWriter.flush();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            sortedFile = null;
        }
        return sortedFile;
    }

    protected void saveToDatabase(List<Long> srcData) {
        String sql = "insert into numbers(val) values(?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            int batchCounter = 0;
            for (Long l : srcData) {
                if (batchCounter == BATCH_SIZE) {
                    statement.executeBatch();
                    batchCounter = 0;
                }
                statement.setLong(1, l);
                statement.addBatch();
                batchCounter++;
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
