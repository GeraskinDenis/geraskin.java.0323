package io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson05.messagefilter.message.MessageConsumer;
import io.ylab.intensive.lesson05.messagefilter.message.MessageHandlerImpl;
import io.ylab.intensive.lesson05.messagefilter.message.MessageSender;
import io.ylab.intensive.lesson05.messagefilter.storage.DBToolsImpl;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.File;

@Configuration
@ComponentScan("io.ylab.intensive.lesson05.messagefilter")
public class Config {

    private final String queueInput = "input";
    private final String queueOutput = "output";
    private final String tableName = "badwords";
    private final File srcFile = new File("src/main/java/io/ylab/intensive/lesson05/messagefilter/resources/badwords.txt");

    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        return connectionFactory;
    }

    @Bean
    public DataSource dataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName("localhost");
        dataSource.setUser("postgres");
        dataSource.setPassword("postgres");
        dataSource.setDatabaseName("postgres");
        dataSource.setPortNumber(5432);
        return dataSource;
    }

    @Bean
    public MessageSender messageSender() {
        return new MessageSender(queueOutput, connectionFactory());
    }

    @Bean
    public MessageHandlerImpl messageHandler() {
        return new MessageHandlerImpl(dataSource(), messageSender());
    }

    @Bean
    public MessageConsumer messageConsumer() {
        return new MessageConsumer(queueInput, connectionFactory(), messageHandler());
    }

    @Bean
    public DBToolsImpl dbTools() {
        if (!srcFile.exists() || srcFile.isDirectory()) {
            throw new RuntimeException("ERROR: The file '" + srcFile.getAbsolutePath() + "'" + "is not exist!");
        }
        DBToolsImpl dbTools = new DBToolsImpl(dataSource(), tableName);
        dbTools.uploadDataToDB(srcFile);
        return dbTools;
    }
}
