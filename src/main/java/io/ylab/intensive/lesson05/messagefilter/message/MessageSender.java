package io.ylab.intensive.lesson05.messagefilter.message;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MessageSender {
    private final String queueName;
    private final ConnectionFactory connectionFactory;

    public MessageSender(String queueName, ConnectionFactory connectionFactory) {
        this.queueName = queueName;
        this.connectionFactory = connectionFactory;
    }

    public void sendMessage(String message) {
        try (Connection connection = connectionFactory.newConnection(); Channel channel = connection.createChannel()) {
            channel.queueDeclare(queueName, false, false, false, null);
            channel.basicPublish("", queueName, null, message.getBytes());
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
