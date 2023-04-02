package io.ylab.intensive.lesson04.filesort;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class FileSortImplNoBatch extends FileSortImpl {
    public FileSortImplNoBatch(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected void saveToDatabase(List<Long> srcData) {
        String sql = "insert into numbers(val) values(?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Long l : srcData) {
                statement.setLong(1, l);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
