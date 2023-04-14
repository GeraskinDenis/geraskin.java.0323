package io.ylab.intensive.lesson05.messagefilter.storage;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class DBToolsImpl implements DBTools {

    private final DataSource dataSource;
    private final String tableName;

    public DBToolsImpl(DataSource dataSource, String tableName) {
        this.dataSource = dataSource;
        this.tableName = tableName;
        initDB();
    }

    @Override
    public void uploadDataToDB(File file) {
        clearTable();
        Set<String> setStrings = new HashSet<>();
        try (FileReader fileReader = new FileReader(file)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while (bufferedReader.ready()) {
                setStrings.add(bufferedReader.readLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String query = String.format("INSERT INTO %s (badword) VALUES(?)", tableName);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            for (String s : setStrings) {
                statement.setString(1, s);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initDB() {
        if (!isExistTable()) {
            createTable();
        }
    }

    private void clearTable() {
        String query = String.format("DELETE FROM %s", tableName);
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected boolean isExistTable() {
        boolean isExistTable;
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, tableName, null);
            isExistTable = resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isExistTable;
    }

    protected void createTable() {
        String query = String.format("CREATE TABLE %s(\n"
                + "\tid bigserial NOT NULL,\n"
                + "\tbadword VARCHAR,\n"
                + "\tPRIMARY KEY (id)\n"
                + ")", tableName);

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
