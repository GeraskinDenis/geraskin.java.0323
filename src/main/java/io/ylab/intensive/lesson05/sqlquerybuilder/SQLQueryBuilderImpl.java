package io.ylab.intensive.lesson05.sqlquerybuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class SQLQueryBuilderImpl implements SQLQueryBuilder {
    private final DataSource dataSource;

    @Autowired
    public SQLQueryBuilderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String queryForTable(String tableName) throws SQLException {
        if (!isTableExist(tableName)) {
            return null;
        }
        List<String> columnNames = getColumns(tableName);
        if (columnNames.size() == 0) {
            return null;
        }
        return getQuery(tableName, columnNames);
    }

    @Override
    public List<String> getTables() throws SQLException {
        Connection connection = dataSource.getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getTables(null, null, null, null);
        List<String> tablesList = new ArrayList<>();
        while (resultSet.next()) {
            tablesList.add(resultSet.getString("TABLE_NAME"));
        }
        connection.close();
        return tablesList;
    }

    private boolean isTableExist(String tableName) throws SQLException {
        Connection connection = dataSource.getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getTables(null, null, tableName, null);
        connection.close();
        return resultSet.next();
    }

    private List<String> getColumns(String tableName) throws SQLException {
        Connection connection = dataSource.getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getColumns(null, null, tableName, null);
        List<String> columnNames = new ArrayList<>();
        while (resultSet.next()) {
            columnNames.add(resultSet.getString("COLUMN_NAME"));
        }
        connection.close();
        return columnNames;
    }

    protected String getQuery(String tableName, List<String> columns) {
        Objects.requireNonNull(columns);
        String sqlTemplate = "SELECT %s FROM %s";
        StringBuilder queryBuilder = new StringBuilder();
        columns.forEach(s -> {
            if (queryBuilder.length() != 0) {
                queryBuilder.append(", ");
            }
            queryBuilder.append(s);
        });

        return String.format(sqlTemplate, queryBuilder, tableName);
    }
}
