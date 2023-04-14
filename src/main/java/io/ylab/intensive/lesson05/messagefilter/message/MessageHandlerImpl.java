package io.ylab.intensive.lesson05.messagefilter.message;

import javax.sql.DataSource;
import java.sql.*;
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
        String query = "SELECT badword FROM badwords WHERE badword = ANY(?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            Array array = connection.createArrayOf("VARCHAR", subStrings);
            statement.setArray(1, array);
            ResultSet resultSet = statement.executeQuery();
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
}
