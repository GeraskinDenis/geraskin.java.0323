package io.ylab.intensive.lesson05.eventsourcing.db.storage;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component("storagePerson")
public class StoragePersonImpl extends AbstractStoragePerson {
    private final DataSource dataSource;

    @Autowired
    public StoragePersonImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void doDeletePerson(Long personId) {
        String sql = "DELETE FROM person WHERE person_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, personId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected List<Person> doFindAll() {
        List<Person> result = new ArrayList<>();
        String sql = "SELECT person_id, first_name, last_name, middle_name FROM person";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Person p = new Person(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4));
                result.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    protected Person doFindPerson(Long personId) {
        String sql = "SELECT person_id, first_name, last_name, middle_name FROM person WHERE person_id = ? LIMIT 1";
        Person result = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, personId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = new Person(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    protected void doSavePerson(Person person) {
        String sql = "INSERT INTO person (person_id, first_name, last_name, middle_name) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, person.getId());
            statement.setString(2, person.getFirstName());
            statement.setString(3, person.getLastName());
            statement.setString(4, person.getMiddleName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(person + " has been saved!");
    }

    @Override
    protected void doUpdatePerson(Person person) {
        String sql = "UPDATE person SET first_name = ?, last_name = ?, middle_name = ? WHERE person_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setString(3, person.getMiddleName());
            statement.setLong(4, person.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(person + " has been updated!");
    }

    @Override
    protected boolean isExistPerson(Long personId) {
        String sql = "SELECT person_id FROM person WHERE person_id = ?";
        boolean result;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, personId);
            ResultSet resultSet = statement.executeQuery();
            result = resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
