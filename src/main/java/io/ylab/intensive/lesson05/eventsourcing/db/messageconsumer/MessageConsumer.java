package io.ylab.intensive.lesson05.eventsourcing.db.messageconsumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import io.ylab.intensive.lesson05.eventsourcing.db.DataProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Component
public class MessageConsumer {
    @Value("myqueue")
    private String queueName;
    @Autowired
    private ConnectionFactory connectionFactory;
    @Autowired
    private DataProcessing dataProcessing;

    public void startMessageDelivery() throws IOException, TimeoutException {
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(queueName, false, false, false, null);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            dataProcessing.messageProcessing(message);
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }


}
