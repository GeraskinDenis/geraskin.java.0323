package geraskindenis.hw4.eventsourcing.api;

import com.rabbitmq.client.ConnectionFactory;
import geraskindenis.hw4.RabbitMQUtil;

public class ApiApp {
    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = initMQ();
        // Тут пишем создание PersonApi, запуск и демонстрацию работы
    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }
}
