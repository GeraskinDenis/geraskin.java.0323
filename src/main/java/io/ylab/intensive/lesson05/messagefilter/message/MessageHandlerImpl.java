package io.ylab.intensive.lesson05.messagefilter.message;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MessageHandlerImpl extends MessageHandlerAbs {

    private final DataSource dataSource;
    private final MessageSender messageSender;

    public MessageHandlerImpl(DataSource dataSource, MessageSender messageSender) {
        this.dataSource = dataSource;
        this.messageSender = messageSender;
    }

    @Override
    protected List<String> getSimilarStrings(String[] subStrings) {
        List<String> similarStrings = new ArrayList<>();
        String pattern = "SELECT badword FROM badwords WHERE badword IN (%s)";
        String str = arrayToString(subStrings);
        String query = String.format(pattern, str);
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                similarStrings.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return similarStrings;
    }

    @Override
    protected void sendMessage(String correctMessage) {
        messageSender.sendMessage(correctMessage);
    }

    private String arrayToString(String[] strings) {
        String pattern = "'%s'";
        StringBuilder builder = new StringBuilder();
        for (String str : strings) {
            if (builder.length() > 0) {
                builder.append(", ");
            }
            builder.append(String.format(pattern, str));
        }
        return builder.toString();
    }
}
