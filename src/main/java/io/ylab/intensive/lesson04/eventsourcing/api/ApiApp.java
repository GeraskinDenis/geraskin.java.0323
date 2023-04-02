package io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.RabbitMQUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ApiApp {

    private final static String QUEUE_NAME = "myqueue";
    private static ConnectionFactory connectionFactory;

    public static void main(String[] args) throws Exception {
        connectionFactory = initMQ();
        // Тут пишем создание PersonApi, запуск и демонстрацию работы
        PersonApi personApi = new PersonApiImpl();
        personApi.savePerson(1L, "FristName1", "LastName1", "MiddleName1");
        personApi.savePerson(2L, "FristName2", "LastName2", "MiddleName2");
        personApi.savePerson(3L, "FristName3", "LastName3", "MiddleName3");
        personApi.savePerson(4L, "FristName4", "LastName4", "MiddleName4");

        personApi.findAll().forEach(System.out::println);

        System.out.println("personApi.deletePerson(4L);");
        personApi.deletePerson(4L);

        personApi.findAll().forEach(System.out::println);

        System.out.println("personApi.findPerson(1L)");
        System.out.println(personApi.findPerson(1L));

        personApi.findAll().forEach(System.out::println);
    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }

    public static void sendMessage(Message message) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}