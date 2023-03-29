package geraskindenis.hw4.eventsourcing.db;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import geraskindenis.hw4.DbUtil;
import geraskindenis.hw4.RabbitMQUtil;
import geraskindenis.hw4.eventsourcing.Person;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbApp {
    private final static String QUEUE_NAME = "myqueue";
    private static DataSource dataSource;

    static {
        try {
            setDataSource();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        initDb();
        ConnectionFactory connectionFactory = initMQ();
        com.rabbitmq.client.Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            messageProcessing(message);
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }

    private static void messageProcessing(String message) {
        String[] strings = message.split(";");
        if (strings[0].equals("delete")) {
            deletePerson(Long.parseLong(strings[1]));
        } else if (strings[0].equals("save")) {
            if (strings.length != 5) {
                throw new RuntimeException("Data format error!");
            }
            Person person = new Person(Long.parseLong(strings[1]),
                    strings[2],
                    strings[3],
                    strings[4]);
            savePerson(person);
        } else {
            throw new RuntimeException("Unknown command!");
        }
    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }

    private static void initDb() throws SQLException {
        String ddl = ""
                + "drop table if exists person;"
                + "create table if not exists person (\n"
                + "person_id bigint primary key,\n"
                + "first_name varchar,\n"
                + "last_name varchar,\n"
                + "middle_name varchar\n"
                + ")";

        DbUtil.applyDdl(ddl, dataSource);
    }

    private static void setDataSource() throws SQLException {
        dataSource = DbUtil.buildDataSource();
    }

    public static void deletePerson(Long personId) {
        String sql = "DELETE FROM person WHERE person_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, personId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Person> findAll() {
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

    public static Person findPerson(Long personId) {
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

    public static boolean isExistPerson(Long personId) {
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

    public static void savePerson(Person person) {
        if (isExistPerson(person.getId())) {
            updatePerson(person);
            return;
        }
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

    private static void updatePerson(Person person) {
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
}