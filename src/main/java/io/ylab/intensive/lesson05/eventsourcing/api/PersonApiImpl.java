package io.ylab.intensive.lesson05.eventsourcing.api;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import io.ylab.intensive.lesson05.eventsourcing.api.command.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Тут пишем реализацию
 */
@Component
public class PersonApiImpl implements PersonApi {

    private final DataSource dataSource;
    private final MessageSender messageSender;

    @Autowired
    public PersonApiImpl(DataSource dataSource,
                         MessageSender messageSender) {
        this.dataSource = dataSource;
        this.messageSender = messageSender;
    }

    @Override
    public void deletePerson(Long personId) {
        messageSender.sendMessage(new Message(Command.DELETE, personId));

    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName) {
        Person p = new Person(personId, firstName, lastName, middleName);
        messageSender.sendMessage(new Message(Command.SAVE, p));
    }

    @Override
    public Person findPerson(Long personId) {
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
    public List<Person> findAll() {
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
}
