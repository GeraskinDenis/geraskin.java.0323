package io.ylab.intensive.lesson05.messagefilter;

import io.ylab.intensive.lesson05.messagefilter.message.MessageConsumer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MessageFilterApp {
    public static void main(String[] args) throws IOException, TimeoutException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        MessageConsumer messageConsumer = applicationContext.getBean(MessageConsumer.class);
        messageConsumer.startMessageDelivery();
    }
}
