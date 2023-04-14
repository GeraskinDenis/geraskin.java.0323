package io.ylab.intensive.lesson05.messagefilter.message;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class MessageConsumer {
    private final String queueName;
    private final ConnectionFactory connectionFactory;
    private final MessageHandler messageHandler;

    public MessageConsumer(String queueName, ConnectionFactory connectionFactory, MessageHandler messageHandler) {
        this.queueName = queueName;
        this.connectionFactory = connectionFactory;
        this.messageHandler = messageHandler;
    }

    public void startMessageDelivery() throws IOException, TimeoutException {
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(queueName, false, false, false, null);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            messageHandler.handleMessage(message);
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}
