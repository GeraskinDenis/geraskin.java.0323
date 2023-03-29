package geraskindenis.hw4.persistentmap;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Класс, методы которого надо реализовать
 */
public class PersistentMapImpl implements PersistentMap {

    private final DataSource dataSource;
    private String mapName;

    public PersistentMapImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void init(String mapName) {
        if (Objects.isNull(mapName) || mapName.isEmpty()) {
            System.out.println("The name of the map cannot be NULL or empty!");
            return;
        }
        this.mapName = mapName;
    }

    @Override
    public boolean containsKey(String key) throws SQLException {
        checkMapName();
        checkKey(key);
        String sql = "select key from persistent_map where map_name = ? and key = ? LIMIT 1";
        boolean result;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, mapName);
            statement.setString(2, key);
            ResultSet resultSet = statement.executeQuery();
            result = resultSet.next();
            resultSet.close();
        }
        return result;
    }

    @Override
    public List<String> getKeys() throws SQLException {
        checkMapName();
        String sql = "SELECT key  FROM  persistent_map WHERE map_name = ?";
        List<String> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, mapName);
            statement.executeQuery();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getString(1));
            }
            resultSet.close();
        }
        return result;
    }

    @Override
    public String get(String key) throws SQLException {
        checkMapName();
        checkKey(key);
        String sql = "SELECT value FROM persistent_map WHERE map_name = ? AND key = ? LIMIT 1";
        String result = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, mapName);
            statement.setString(2, key);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getString(1);
            }
            resultSet.close();
        }
        return result;
    }

    @Override
    public void remove(String key) throws SQLException {
        checkMapName();
        checkKey(key);
        String sql = "DELETE FROM persistent_map WHERE map_name = ? AND key = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, mapName);
            statement.setString(2, key);
            statement.executeUpdate();
        }
    }

    @Override
    public void put(String key, String value) throws SQLException {
        remove(key);
        String sql = "INSERT INTO persistent_map (map_name, key, value) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, mapName);
            statement.setString(2, key);
            statement.setString(3, value);
            statement.executeUpdate();
        }
    }

    @Override
    public void clear() throws SQLException {
        checkMapName();
        String sql = "DELETE FROM persistent_map WHERE map_name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, mapName);
            statement.executeUpdate();
        }
    }

    private void checkMapName() {
        if (Objects.isNull(mapName) || mapName.isEmpty()) {
            throw new RuntimeException("The name of the map cannot be NULL or empty!");
        }
    }

    private void checkKey(String key) {
        if (Objects.isNull(key) || key.isEmpty()) {
            throw new RuntimeException("The key of the map cannot be NULL or empty!");
        }
    }
}
